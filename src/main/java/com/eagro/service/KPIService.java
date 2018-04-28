package com.eagro.service;

import com.eagro.service.dto.KPIDTO;
import java.util.List;

/**
 * Service Interface for managing KPI.
 */
public interface KPIService {

    /**
     * Save a kPI.
     *
     * @param kPIDTO the entity to save
     * @return the persisted entity
     */
    KPIDTO save(KPIDTO kPIDTO);

    /**
     * Get all the kPIS.
     *
     * @return the list of entities
     */
    List<KPIDTO> findAll();

    /**
     * Get the "id" kPI.
     *
     * @param id the id of the entity
     * @return the entity
     */
    KPIDTO findOne(Long id);

    /**
     * Delete the "id" kPI.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
