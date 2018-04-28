package com.eagro.service;

import com.eagro.service.dto.SectionSensorMappingDTO;
import java.util.List;

/**
 * Service Interface for managing SectionSensorMapping.
 */
public interface SectionSensorMappingService {

    /**
     * Save a sectionSensorMapping.
     *
     * @param sectionSensorMappingDTO the entity to save
     * @return the persisted entity
     */
    SectionSensorMappingDTO save(SectionSensorMappingDTO sectionSensorMappingDTO);

    /**
     * Get all the sectionSensorMappings.
     *
     * @return the list of entities
     */
    List<SectionSensorMappingDTO> findAll();

    /**
     * Get the "id" sectionSensorMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SectionSensorMappingDTO findOne(Long id);

    /**
     * Delete the "id" sectionSensorMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
