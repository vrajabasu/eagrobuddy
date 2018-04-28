package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.UserLayoutMapping;
import com.eagro.repository.UserLayoutMappingRepository;
import com.eagro.service.UserLayoutMappingService;
import com.eagro.service.dto.UserLayoutMappingDTO;
import com.eagro.service.mapper.UserLayoutMappingMapper;

/**
 * Service Implementation for managing UserLayoutMapping.
 */
@Service
@Transactional
public class UserLayoutMappingServiceImpl implements UserLayoutMappingService {

    private final Logger log = LoggerFactory.getLogger(UserLayoutMappingServiceImpl.class);

    @Autowired
    public UserLayoutMappingRepository userLayoutMappingRepository;

    @Autowired
    public UserLayoutMappingMapper userLayoutMappingMapper;

    
    /**
     * Save a userLayoutMapping.
     *
     * @param userLayoutMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserLayoutMappingDTO save(UserLayoutMappingDTO userLayoutMappingDTO) {
        log.debug("Request to save UserLayoutMapping : {}", userLayoutMappingDTO);
        UserLayoutMapping userLayoutMapping = userLayoutMappingMapper.toEntity(userLayoutMappingDTO);
        userLayoutMapping = userLayoutMappingRepository.save(userLayoutMapping);
        return userLayoutMappingMapper.toDto(userLayoutMapping);
    }

    /**
     * Get all the userLayoutMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserLayoutMappingDTO> findAll() {
        log.debug("Request to get all UserLayoutMappings");
        return userLayoutMappingRepository.findAll().stream()
            .map(userLayoutMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userLayoutMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserLayoutMappingDTO findOne(Long id) {
        log.debug("Request to get UserLayoutMapping : {}", id);
        UserLayoutMapping userLayoutMapping = userLayoutMappingRepository.findOne(id);
        return userLayoutMappingMapper.toDto(userLayoutMapping);
       
    }

    /**
     * Delete the userLayoutMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserLayoutMapping : {}", id);
        userLayoutMappingRepository.delete(id);
    }
}
