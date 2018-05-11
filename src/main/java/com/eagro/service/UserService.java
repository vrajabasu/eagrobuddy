package com.eagro.service;

import com.eagro.service.dto.UserDTO;
import java.util.List;

/**
 * Service Interface for managing EagroUser.
 */
public interface UserService {

    /**
     * Save a eagroUser.
     *
     * @param eagroUserDTO the entity to save
     * @return the persisted entity
     */
    UserDTO save(UserDTO eagroUserDTO);

    /**
     * Get all the eagroUsers.
     *
     * @return the list of entities
     */
    List<UserDTO> findAll();
    /**
     * Get all the EagroUserDTO where UserRole is null.
     *
     * @return the list of entities
     */
  //  List<UserDTO> findAllWhereUserRoleIsNull();

    /**
     * Get the "id" eagroUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserDTO findOne(Long id);

    /**
     * Delete the "id" eagroUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
