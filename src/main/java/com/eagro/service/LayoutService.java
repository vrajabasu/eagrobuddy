package com.eagro.service;

import com.eagro.service.dto.LayoutDTO;
import java.util.List;

/**
 * Service Interface for managing Layout.
 */
public interface LayoutService {

    /**
     * Save a layout.
     *
     * @param layoutDTO the entity to save
     * @return the persisted entity
     */
    LayoutDTO save(LayoutDTO layoutDTO);

    /**
     * Get all the layouts.
     *
     * @return the list of entities
     */
    List<LayoutDTO> findAll();

    /**
     * Get the "id" layout.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LayoutDTO findOne(Long id);

    /**
     * Delete the "id" layout.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
