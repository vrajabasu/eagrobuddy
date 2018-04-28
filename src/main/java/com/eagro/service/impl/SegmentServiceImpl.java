package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Segment;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.SegmentService;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.mapper.SegmentMapper;

/**
 * Service Implementation for managing Segment.
 */
@Service
@Transactional
public class SegmentServiceImpl implements SegmentService {

    private final Logger log = LoggerFactory.getLogger(SegmentServiceImpl.class);

    @Autowired
    public SegmentRepository segmentRepository;

    @Autowired
    public SegmentMapper segmentMapper;

   
    /**
     * Save a segment.
     *
     * @param segmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SegmentDTO save(SegmentDTO segmentDTO) {
        log.debug("Request to save Segment : {}", segmentDTO);
        Segment segment = segmentMapper.toEntity(segmentDTO);
        segment = segmentRepository.save(segment);
        return segmentMapper.toDto(segment);
    }

    /**
     * Get all the segments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SegmentDTO> findAll() {
        log.debug("Request to get all Segments");
        return segmentRepository.findAll().stream()
            .map(segmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one segment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SegmentDTO findOne(Long id) {
        log.debug("Request to get Segment : {}", id);
       Segment segment = segmentRepository.findOne(id);
        return segmentMapper.toDto(segment);
    }

    /**
     * Delete the segment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Segment : {}", id);
        segmentRepository.delete(id);
    }
}
