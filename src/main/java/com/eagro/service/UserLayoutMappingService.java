package com.eagro.service;

import com.eagro.service.dto.UserLayoutMappingDTO;
import java.util.List;

/**
 * Service Interface for managing UserLayoutMapping.
 */
public interface UserLayoutMappingService {

    /**
     * Save a userLayoutMapping.
     *
     * @param userLayoutMappingDTO the entity to save
     * @return the persisted entity
     */
    UserLayoutMappingDTO save(UserLayoutMappingDTO userLayoutMappingDTO);

    /**
     * Get all the userLayoutMappings.
     *
     * @return the list of entities
     */
    List<UserLayoutMappingDTO> findAll();

    /**
     * Get the "id" userLayoutMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserLayoutMappingDTO findOne(Long id);

    /**
     * Delete the "id" userLayoutMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
