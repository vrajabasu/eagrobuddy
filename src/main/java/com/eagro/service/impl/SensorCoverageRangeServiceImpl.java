package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.SensorCoverageRange;
import com.eagro.repository.SensorCoverageRangeRepository;
import com.eagro.service.SensorCoverageRangeService;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.mapper.SensorCoverageRangeMapper;

/**
 * Service Implementation for managing SensorCoverageRange.
 */
@Service
@Transactional
public class SensorCoverageRangeServiceImpl implements SensorCoverageRangeService {

    private final Logger log = LoggerFactory.getLogger(SensorCoverageRangeServiceImpl.class);

    @Autowired
    public SensorCoverageRangeRepository sensorCoverageRangeRepository;

    @Autowired
    public SensorCoverageRangeMapper sensorCoverageRangeMapper;


    /**
     * Save a sensorCoverageRange.
     *
     * @param sensorCoverageRangeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SensorCoverageRangeDTO save(SensorCoverageRangeDTO sensorCoverageRangeDTO) {
        log.debug("Request to save SensorCoverageRange : {}", sensorCoverageRangeDTO);
        SensorCoverageRange sensorCoverageRange = sensorCoverageRangeMapper.toEntity(sensorCoverageRangeDTO);
        sensorCoverageRange = sensorCoverageRangeRepository.save(sensorCoverageRange);
        return sensorCoverageRangeMapper.toDto(sensorCoverageRange);
    }

    /**
     * Get all the sensorCoverageRanges.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SensorCoverageRangeDTO> findAll() {
        log.debug("Request to get all SensorCoverageRanges");
        return sensorCoverageRangeRepository.findAll().stream()
            .map(sensorCoverageRangeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sensorCoverageRange by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SensorCoverageRangeDTO findOne(Long id) {
        log.debug("Request to get SensorCoverageRange : {}", id);
       SensorCoverageRange sensorCoverageRange = sensorCoverageRangeRepository.findOne(id);
        return sensorCoverageRangeMapper.toDto(sensorCoverageRange);
       
    }

    /**
     * Delete the sensorCoverageRange by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SensorCoverageRange : {}", id);
        sensorCoverageRangeRepository.delete(id);
    }
}
