package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.SensorData;
import com.eagro.repository.SensorDataRepository;
import com.eagro.service.SensorDataService;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.mapper.SensorDataMapper;

/**
 * Service Implementation for managing SensorData.
 */
@Service
@Transactional
public class SensorDataServiceImpl implements SensorDataService {

    private final Logger log = LoggerFactory.getLogger(SensorDataServiceImpl.class);

    @Autowired
    public SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataMapper sensorDataMapper;

    /**
     * Save a sensorData.
     *
     * @param sensorDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SensorDataDTO save(SensorDataDTO sensorDataDTO) {
        log.debug("Request to save SensorData : {}", sensorDataDTO);
        SensorData sensorData = sensorDataMapper.toEntity(sensorDataDTO);
        sensorData = sensorDataRepository.save(sensorData);
        return sensorDataMapper.toDto(sensorData);
    }

    /**
     * Get all the sensorData.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SensorDataDTO> findAll() {
        log.debug("Request to get all SensorData");
        return sensorDataRepository.findAll().stream()
            .map(sensorDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sensorData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SensorDataDTO findOne(Long id) {
        log.debug("Request to get SensorData : {}", id);
       SensorData sensorData = sensorDataRepository.findOne(id);
        return sensorDataMapper.toDto(sensorData);
        
    }

    /**
     * Delete the sensorData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SensorData : {}", id);
        sensorDataRepository.delete(id);
    }
}
