package com.eagro.service;

import com.eagro.service.dto.SensorDTO;
import java.util.List;

/**
 * Service Interface for managing Sensor.
 */
public interface SensorService {

    /**
     * Save a sensor.
     *
     * @param sensorDTO the entity to save
     * @return the persisted entity
     */
    SensorDTO save(SensorDTO sensorDTO);

    /**
     * Get all the sensors.
     *
     * @return the list of entities
     */
    List<SensorDTO> findAll();

    /**
     * Get the "id" sensor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SensorDTO findOne(Long id);

    /**
     * Delete the "id" sensor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
