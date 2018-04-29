package com.eagro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Layout;
import com.eagro.entities.Section;
import com.eagro.entities.Segment;
import com.eagro.repository.LayoutRepository;
import com.eagro.repository.SectionRepository;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.component.SegmentDetailsService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentsResponseDTO;
import com.eagro.service.dto.SegmentsResponseDTO.OverallThresholdstateEnum;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.mapper.LayoutMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionMapper;
import com.eagro.service.mapper.SegmentMapper;
import com.eagro.service.utils.ServiceUtil;

/**
 * The Class LayoutVisualizationServiceImpl.
 */
@Service
@Transactional
public class LayoutVisualizationServiceImpl implements LayoutVisualizationService {

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
		LayoutDTO layoutDTO = layoutMapper.toDto(layout);
		// Enrich layout information in response
		LayoutResponseDTO layoutResponseDTO = null;
		if (layoutDTO != null) {
			layoutResponseDTO = layoutVisualizationMapper.layouttoLayoutResponse(layoutDTO);
			log.debug("layout information : {}", layoutResponseDTO);
		}
		// Fetch all section info
		List<Section> sectionList = sectionRepository.findByLayoutId(layoutId);
		List<SectionDTO> sectionDTOList = sectionMapper.toDto(sectionList);
		// Enrich section info to response
		if (layoutResponseDTO != null && ServiceUtil.isNotEmptyResult(sectionDTOList)) {
			List<SectionsResponseDTO> sectionResponseDTOList = new ArrayList<>();
			// Iterate the section
			for (SectionDTO sectionDTO : sectionDTOList) {
				if (sectionDTO != null) {
					// Enrich section details to the response
					SectionsResponseDTO sectionResponseDTO = new SectionsResponseDTO();
					sectionResponseDTO = layoutVisualizationMapper.sectiontoSectionResponse(sectionDTO);
					// Retrieve the list of segments for specific section
					List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId,
							sectionDTO.getSectionId());
					List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
					List<SegmentsResponseDTO> segmentsResponseDTOList = new ArrayList<>();
					for (SegmentDTO segment : segmentDTOList) {
						// Enrich segment details to the response
						SegmentsResponseDTO segmentsResponseDTO = layoutVisualizationMapper
								.segmenttoSegmentsResponseDTO(segment);
						// Retrieve sensor applicable for segment
						Map<Long, SectionSensorMappingDTO> segmentSensorMap = segmentDetailsService
								.retrieveSensorForCurrentSegment(sectionDTO, segment);
						// Retrieve Optimal KPI values
						if (segmentSensorMap != null && !segmentSensorMap.isEmpty()) {
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
									// Enrich the overall Threshold state to segment response
									segmentsResponseDTO.setOverallThresholdstate(thresholdState);
									segmentsResponseDTOList.add(segmentsResponseDTO);
								}
							}
						}
					}
					//Enrich the segments list to response
					sectionResponseDTO.setSegments(segmentsResponseDTOList);
					sectionResponseDTOList.add(sectionResponseDTO);
				}
			}
			//Enrich the sections list to response
			layoutResponseDTO.setSections(sectionResponseDTOList);
		}

		return layoutResponseDTO;
	}

	@Override
	public SectionsResponseDTO getSectionDetails(Long layoutId, Long sectionId) {
		// TODO Auto-generated method stub
		//
		return null;
	}

}
