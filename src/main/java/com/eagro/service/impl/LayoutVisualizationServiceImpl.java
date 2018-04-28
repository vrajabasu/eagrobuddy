package com.eagro.service.impl;

import java.util.List;

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
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.mapper.LayoutMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionMapper;
import com.eagro.service.mapper.SegmentMapper;

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
		Layout layout = layoutRepository.findOneWithEagerRelationships(layoutId);
		LayoutDTO layoutDTO = layoutMapper.toDto(layout);
		// Enrich layout information in response
		LayoutResponseDTO layoutResponseDTO = layoutVisualizationMapper.layouttoLayoutResponse(layoutDTO);
		log.debug("layout information : {}", layoutResponseDTO);
		// Fetch all section info
		List<Section> sectionList = sectionRepository.findByLayoutId(layoutId);
		List<SectionDTO> sectionDTOList = sectionMapper.toDto(sectionList);
		// Enrich section info to response
		List<SectionsResponseDTO> sections = layoutVisualizationMapper.sectiontoSectionResponseList(sectionDTOList);
		layoutResponseDTO.setSections(sections);
		// Iterate the section
		for (SectionsResponseDTO section : sections) {
			
			List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId, section.getSectionId());
			List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
			

		}
		return null;
	}

}
