package com.eagro.service;

import com.eagro.service.dto.SensorCoverageRangeDTO;
import java.util.List;

/**
 * Service Interface for managing SensorCoverageRange.
 */
public interface SensorCoverageRangeService {

    /**
     * Save a sensorCoverageRange.
     *
     * @param sensorCoverageRangeDTO the entity to save
     * @return the persisted entity
     */
    SensorCoverageRangeDTO save(SensorCoverageRangeDTO sensorCoverageRangeDTO);

    /**
     * Get all the sensorCoverageRanges.
     *
     * @return the list of entities
     */
    List<SensorCoverageRangeDTO> findAll();

    /**
     * Get the "id" sensorCoverageRange.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SensorCoverageRangeDTO findOne(Long id);

    /**
     * Delete the "id" sensorCoverageRange.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
