package com.eagro.service;

import com.eagro.service.dto.SegmentDTO;
import java.util.List;

/**
 * Service Interface for managing Segment.
 */
public interface SegmentService {

    /**
     * Save a segment.
     *
     * @param segmentDTO the entity to save
     * @return the persisted entity
     */
    SegmentDTO save(SegmentDTO segmentDTO);

    /**
     * Get all the segments.
     *
     * @return the list of entities
     */
    List<SegmentDTO> findAll();

    /**
     * Get the "id" segment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SegmentDTO findOne(Long id);

    /**
     * Delete the "id" segment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
