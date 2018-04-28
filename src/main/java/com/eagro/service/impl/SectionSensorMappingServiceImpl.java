package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.SectionSensorMapping;
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.service.SectionSensorMappingService;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.mapper.SectionSensorMappingMapper;

/**
 * Service Implementation for managing SectionSensorMapping.
 */
@Service
@Transactional
public class SectionSensorMappingServiceImpl implements SectionSensorMappingService {

    private final Logger log = LoggerFactory.getLogger(SectionSensorMappingServiceImpl.class);

    @Autowired
    public SectionSensorMappingRepository sectionSensorMappingRepository;

    @Autowired
    public SectionSensorMappingMapper sectionSensorMappingMapper;

   
    /**
     * Save a sectionSensorMapping.
     *
     * @param sectionSensorMappingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SectionSensorMappingDTO save(SectionSensorMappingDTO sectionSensorMappingDTO) {
        log.debug("Request to save SectionSensorMapping : {}", sectionSensorMappingDTO);
        SectionSensorMapping sectionSensorMapping = sectionSensorMappingMapper.toEntity(sectionSensorMappingDTO);
        sectionSensorMapping = sectionSensorMappingRepository.save(sectionSensorMapping);
        return sectionSensorMappingMapper.toDto(sectionSensorMapping);
    }

    /**
     * Get all the sectionSensorMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SectionSensorMappingDTO> findAll() {
        log.debug("Request to get all SectionSensorMappings");
        return sectionSensorMappingRepository.findAll().stream()
            .map(sectionSensorMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sectionSensorMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SectionSensorMappingDTO findOne(Long id) {
        log.debug("Request to get SectionSensorMapping : {}", id);
        SectionSensorMapping sectionSensorMapping = sectionSensorMappingRepository.findOne(id);
        return sectionSensorMappingMapper.toDto(sectionSensorMapping);
        
    }

    /**
     * Delete the sectionSensorMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SectionSensorMapping : {}", id);
        sectionSensorMappingRepository.delete(id);
    }
}
