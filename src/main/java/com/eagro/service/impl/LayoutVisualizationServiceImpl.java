package com.eagro.service.impl;

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
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.enumeration.ThresholdState;
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
	public LayoutResponseDTO generateLayout(Long layoutId) {
		// TODO

		// Fetch layout information from layout table
		log.debug("Request to get Layout  : {}", layoutId);
		if (layoutId == null) {
			log.error("layout Id is invalid");
		} else {
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
				List<SectionsResponseDTO> sections = layoutVisualizationMapper
						.sectiontoSectionResponseList(sectionDTOList);
				layoutResponseDTO.setSections(sections);
				// Iterate the section
				for (SectionDTO section : sectionDTOList) {
					if (section != null) {
						List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId,
								section.getSectionId());
						List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
					
						for (SegmentDTO segment : segmentDTOList) {
							SegmentsResponseDTO segments = layoutVisualizationMapper
									.segmenttoSegmentsResponseDTO(segment);
							// Retrieve sensor applicable for segment
							Map<Long, SectionSensorMappingDTO> segmentSensorMap = segmentDetailsService
									.retrieveSensorForCurrentSegment(section, segment);
							// Retrieve Optimal KPI values
							if (segmentSensorMap != null && !segmentSensorMap.isEmpty()) {
								Map<Long, List<KPIDTO>> sensorOptimalKPIMap = segmentDetailsService
										.retrieveOptimalKPIsForSensors(section, segmentSensorMap);
								if (sensorOptimalKPIMap != null && !sensorOptimalKPIMap.isEmpty()) {
									// Retrieve Actual KPI Values
									Map<Long, SensorDataDTO> sensorActualValueMap = segmentDetailsService
											.retrieveCurrentDataFromSensors(segmentSensorMap);
									// calculate overall threshold
									if (sensorActualValueMap != null && !sensorActualValueMap.isEmpty()) {
										ThresholdState thresholdState = segmentDetailsService
												.calculateOverallThresholdState(sensorActualValueMap,
														sensorOptimalKPIMap);
										
										
										
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

}
