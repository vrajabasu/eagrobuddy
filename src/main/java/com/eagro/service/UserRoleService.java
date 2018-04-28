package com.eagro.service;

import com.eagro.service.dto.UserRoleDTO;
import java.util.List;

/**
 * Service Interface for managing UserRole.
 */
public interface UserRoleService {

    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save
     * @return the persisted entity
     */
    UserRoleDTO save(UserRoleDTO userRoleDTO);

    /**
     * Get all the userRoles.
     *
     * @return the list of entities
     */
    List<UserRoleDTO> findAll();

    /**
     * Get the "id" userRole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserRoleDTO findOne(Long id);

    /**
     * Delete the "id" userRole.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
