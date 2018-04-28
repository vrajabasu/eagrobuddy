package com.eagro.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Sensor;
import com.eagro.repository.SensorRepository;
import com.eagro.service.SensorService;
import com.eagro.service.dto.SensorDTO;
import com.eagro.service.mapper.SensorMapper;

/**
 * Service Implementation for managing Sensor.
 */
@Service
@Transactional
public class SensorServiceImpl implements SensorService {

    private final Logger log = LoggerFactory.getLogger(SensorServiceImpl.class);

    @Autowired
    public SensorRepository sensorRepository;

    @Autowired
    public SensorMapper sensorMapper;


    /**
     * Save a sensor.
     *
     * @param sensorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SensorDTO save(SensorDTO sensorDTO) {
        log.debug("Request to save Sensor : {}", sensorDTO);
        Sensor sensor = sensorMapper.toEntity(sensorDTO);
        sensor = sensorRepository.save(sensor);
        return sensorMapper.toDto(sensor);
    }

    /**
     * Get all the sensors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SensorDTO> findAll() {
        log.debug("Request to get all Sensors");
        return sensorRepository.findAllWithEagerRelationships().stream()
            .map(sensorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sensor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SensorDTO findOne(Long id) {
        log.debug("Request to get Sensor : {}", id);
        Sensor sensor = sensorRepository.findOneWithEagerRelationships(id);
        return sensorMapper.toDto(sensor);
    }

    /**
     * Delete the sensor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sensor : {}", id);
        sensorRepository.delete(id);
    }
}
