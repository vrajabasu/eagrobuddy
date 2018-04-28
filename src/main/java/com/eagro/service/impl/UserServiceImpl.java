package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.User;
import com.eagro.repository.UserRepository;
import com.eagro.service.UserService;
import com.eagro.service.dto.UserDTO;
import com.eagro.service.mapper.UserMapper;

/**
 * Service Implementation for managing EagroUser.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserRepository eagroUserRepository;

    @Autowired
    public UserMapper eagroUserMapper;

    /**
     * Save a eagroUser.
     *
     * @param eagroUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserDTO save(UserDTO eagroUserDTO) {
        log.debug("Request to save EagroUser : {}", eagroUserDTO);
       User eagroUser = eagroUserMapper.toEntity(eagroUserDTO);
        eagroUser = eagroUserRepository.save(eagroUser);
        return eagroUserMapper.toDto(eagroUser);
    }

    /**
     * Get all the eagroUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Request to get all EagroUsers");
        return eagroUserRepository.findAllWithEagerRelationships().stream()
            .map(eagroUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the eagroUsers where UserRole is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<UserDTO> findAllWhereUserRoleIsNull() {
        log.debug("Request to get all eagroUsers where UserRole is null");
        return StreamSupport
            .stream(eagroUserRepository.findAll().spliterator(), false)
            .filter(eagroUser -> eagroUser.getUserRole() == null)
            .map(eagroUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eagroUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserDTO findOne(Long id) {
        log.debug("Request to get EagroUser : {}", id);
        User eagroUser = eagroUserRepository.findOneWithEagerRelationships(id);
        return eagroUserMapper.toDto(eagroUser);
    }

    /**
     * Delete the eagroUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EagroUser : {}", id);
        eagroUserRepository.delete(id);
    }
}
