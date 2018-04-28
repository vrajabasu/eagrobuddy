package com.eagro.service;

import com.eagro.service.dto.SensorDataDTO;
import java.util.List;

/**
 * Service Interface for managing SensorData.
 */
public interface SensorDataService {

    /**
     * Save a sensorData.
     *
     * @param sensorDataDTO the entity to save
     * @return the persisted entity
     */
    SensorDataDTO save(SensorDataDTO sensorDataDTO);

    /**
     * Get all the sensorData.
     *
     * @return the list of entities
     */
    List<SensorDataDTO> findAll();

    /**
     * Get the "id" sensorData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SensorDataDTO findOne(Long id);

    /**
     * Delete the "id" sensorData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
