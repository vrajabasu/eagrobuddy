package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.UserRole;
import com.eagro.repository.UserRoleRepository;
import com.eagro.service.UserRoleService;
import com.eagro.service.dto.UserRoleDTO;
import com.eagro.service.mapper.UserRoleMapper;

/**
 * Service Implementation for managing UserRole.
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    public UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleMapper userRoleMapper;

    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserRoleDTO save(UserRoleDTO userRoleDTO) {
        log.debug("Request to save UserRole : {}", userRoleDTO);
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    /**
     * Get all the userRoles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserRoleDTO> findAll() {
        log.debug("Request to get all UserRoles");
        return userRoleRepository.findAll().stream()
            .map(userRoleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userRole by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserRoleDTO findOne(Long id) {
        log.debug("Request to get UserRole : {}", id);
        UserRole userRole = userRoleRepository.findOne(id);
        return userRoleMapper.toDto(userRole);
       
    }

    /**
     * Delete the userRole by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRole : {}", id);
        userRoleRepository.delete(id);
    }
}
