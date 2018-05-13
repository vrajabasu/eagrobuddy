package com.eagro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Layout;
import com.eagro.entities.Section;
import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.Segment;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.LayoutRepository;
import com.eagro.repository.SectionRepository;
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.component.SegmentDetailsService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.OptimalKpiValueResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentsResponseDTO;
import com.eagro.service.dto.SegmentsResponseDTO.OverallThresholdstateEnum;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.mapper.LayoutMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
import com.eagro.service.mapper.SegmentMapper;
import com.eagro.service.utils.ServiceUtil;

/**
 * The Class LayoutVisualizationServiceImpl.
 */
@Service
@Transactional
public class LayoutVisualizationServiceImpl implements LayoutVisualizationService {

	private static final String LAYOUT_NOT_AVAILABLE = "Layout Details is not Available";
	private static final String SECTION_NOT_AVAILABLE = "Section still not mapped to the layout / Section Not available";
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LayoutVisualizationServiceImpl.class);
	@Autowired
	public LayoutRepository layoutRepository;
	@Autowired
	public LayoutMapper layoutMapper;
	@Autowired
	public SegmentDetailsService segmentDetailsService;
	@Autowired
	public LayoutVisualizationMapper layoutVisualizationMapper;
	@Autowired
	public SectionRepository sectionRepository;
	@Autowired
	public SectionMapper sectionMapper;
	@Autowired
	public SegmentRepository segmentRepository;
	@Autowired
	public SegmentMapper segmentMapper;
	@Autowired
	public SectionSensorMappingRepository sectionSensorMappingRepository;
	@Autowired
	public SectionSensorMappingMapper sectionSensorMappingMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.eagro.service.LayoutVisualizationService#generateLayout(java.lang.
	 * Long)
	 */
	@Override
	public LayoutResponseDTO getLayoutDetails(Long layoutId) {
		// Fetch layout information from layout table
		log.debug("Request to get Layout  : {}", layoutId);
		Layout layout = layoutRepository.findOne(layoutId);
		log.debug("layout information fetched from DB: {}", layout);
		LayoutDTO layoutDTO = layoutMapper.toDto(layout);
		// Enrich layout information in response
		LayoutResponseDTO layoutResponseDTO = new LayoutResponseDTO();
		if (layoutDTO == null) {
			log.error(LAYOUT_NOT_AVAILABLE);
		} else {
		layoutResponseDTO = layoutVisualizationMapper.layouttoLayoutResponse(layoutDTO);
		log.debug("Enriched the layout information to the Response : {}", layoutResponseDTO);
		
		// Fetch all section info
		List<Section> sectionList = sectionRepository.findByLayoutId(layoutId);
		log.debug("section information fetched from DB: {}", sectionList);
		
		List<SectionDTO> sectionDTOList = sectionMapper.toDto(sectionList);

		log.debug("List of section information : {} for layout : {}", sectionDTOList, layoutId);
		// Enrich section info to response
		if (layoutResponseDTO != null && ServiceUtil.isNotEmptyResult(sectionDTOList)) {
			List<SectionsResponseDTO> sectionResponseDTOList = new ArrayList<>();
			// Iterate the section
			for (SectionDTO sectionDTO : sectionDTOList) {
				SectionsResponseDTO sectionResponseDTO = null;
				if (sectionDTO != null) {
					sectionResponseDTO = fetchPerSectionDetails(layoutId, sectionDTO);
				}
				sectionResponseDTOList.add(sectionResponseDTO);
			}
			// Enrich the sections list to response
			layoutResponseDTO.setSections(sectionResponseDTOList);
		}
		}
		return layoutResponseDTO;
	}

	private SectionsResponseDTO fetchPerSectionDetails(Long layoutId, SectionDTO sectionDTO) {
		SectionsResponseDTO sectionResponseDTO = new SectionsResponseDTO();
		
			// Enrich section details to the response
			sectionResponseDTO = layoutVisualizationMapper.sectiontoSectionResponse(sectionDTO);
			log.debug("Enrich Section value to Response : {} ", sectionResponseDTO);

			// Retrieve the list of segments for specific section
			List<SegmentDTO> segmentDTOList = retrieveSegment(layoutId, sectionDTO);
			if (ServiceUtil.isNotEmptyResult(segmentDTOList)) {

				List<SegmentsResponseDTO> segmentsResponseDTOList = new ArrayList<>();
				for (SegmentDTO segment : segmentDTOList) {
					// Enrich segment details to the response
					SegmentsResponseDTO segmentsResponseDTO = layoutVisualizationMapper
							.segmenttoSegmentsResponseDTO(segment);
					// Retrieve sensor applicable for segment
					log.debug("Enriched segment details to the Response : {}", segmentsResponseDTO);
					
					Map<Long, SectionSensorMappingDTO> segmentSensorMap = segmentDetailsService
							.retrieveSensorForCurrentSegment(sectionDTO, segment);
					// Retrieve Optimal KPI values
					if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
						Map<Long, List<KPIDTO>> sensorOptimalKPIMap = segmentDetailsService
								.retrieveOptimalKPIsForSensors(sectionDTO, segmentSensorMap);
						if (sensorOptimalKPIMap != null && !sensorOptimalKPIMap.isEmpty()) {
							// Retrieve Actual KPI Values
							Map<Long, SensorDataDTO> sensorActualValueMap = segmentDetailsService
									.retrieveCurrentDataFromSensors(segmentSensorMap);
							// calculate overall threshold
							if (sensorActualValueMap != null && !sensorActualValueMap.isEmpty()) {
								OverallThresholdstateEnum thresholdState = segmentDetailsService
										.calculateOverallThresholdState(sensorActualValueMap, sensorOptimalKPIMap);
								// Enrich the overall Threshold state to segment
								// response
								segmentsResponseDTO.setOverallThresholdstate(thresholdState);
								segmentsResponseDTOList.add(segmentsResponseDTO);
							}
						}
					}
				}
				// Enrich the segments list to response
				sectionResponseDTO.setSegments(segmentsResponseDTOList);
			log.debug("Enriched complete Data for section details to the Response : {}", sectionResponseDTO);
		}
		return sectionResponseDTO;

	}

	private List<SegmentDTO> retrieveSegment(Long layoutId, SectionDTO sectionDTO) {
		List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId, sectionDTO.getSectionId());
		log.debug("Segment details fetched from DB : {} ", segmentList);

		List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDTOList, layoutId);
		return segmentDTOList;
	}

	@Override
	public SectionsResponseDTO getSectionDetails(Long layoutId, Long sectionId) {
		log.debug("Request to get Section details based on layoutId  : {} and sectionId : {}", layoutId, sectionId);
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);
		return fetchPerSectionDetails(layoutId, sectionDTO);
	}

	private SectionDTO retrieveSectionBasicInfo(Long layoutId, Long sectionId) {
		Section section = sectionRepository.findByLayoutIdAndSectionId(layoutId, sectionId);
		SectionDTO sectionDTO = sectionMapper.toDto(section);
		return sectionDTO;
	}

	@Override
	public SectionwithkpiResponseDTO getSectionBasedOptimalKpi(Long sectionId, Long layoutId) {
		// TODO Auto-generated method stub
		log.debug("Request to get Section Based Optimal Kpi values by layoutId  : {} and sectionId : {}", layoutId,
				sectionId);
		// To fetch sectionDetails
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);

		List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository
				.findBySectionIdAndLayoutId(layoutId, sectionId);
		List<SectionSensorMappingDTO> sectionSensorMappingDTOList = sectionSensorMappingMapper
				.toDto(sectionSensorMappingList);
		SectionwithkpiResponseDTO sectionWithKpiResponseDTO = new SectionwithkpiResponseDTO();

		Map<ZoneType, List<SectionSensorMappingDTO>> zoneBasedSectionMappings = sectionSensorMappingDTOList.stream()
				.collect(Collectors.groupingBy(p -> p.getZoneType(), Collectors.toList()));
		List<OptimalKpiValueResponseDTO> optimalValueList = new ArrayList<OptimalKpiValueResponseDTO>();
		zoneBasedSectionMappings.keySet().forEach(zoneType -> {
			List<KPIDTO> kpiDTO = segmentDetailsService.retrieveKpi(layoutId, sectionId, zoneType);

		});
		// fetch KPI for each zonetype

		return null;
	}

}
