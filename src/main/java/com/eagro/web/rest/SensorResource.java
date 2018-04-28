package com.eagro.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.service.SensorService;
import com.eagro.service.dto.SensorDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing Sensor.
 */
@RestController
@RequestMapping("/api")
public class SensorResource {

    private final Logger log = LoggerFactory.getLogger(SensorResource.class);

    private static final String ENTITY_NAME = "sensor";

    @Autowired
    public SensorService sensorService;


    /**
     * POST  /sensors : Create a new sensor.
     *
     * @param sensorDTO the sensorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensorDTO, or with status 400 (Bad Request) if the sensor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensors",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SensorDTO> createSensor(@RequestBody SensorDTO sensorDTO) throws URISyntaxException {
        log.debug("REST request to save Sensor : {}", sensorDTO);
        if (sensorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sensor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorDTO result = sensorService.save(sensorDTO);
        return ResponseEntity.created(new URI("/api/sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensors : Updates an existing sensor.
     *
     * @param sensorDTO the sensorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensorDTO,
     * or with status 400 (Bad Request) if the sensorDTO is not valid,
     * or with status 500 (Internal Server Error) if the sensorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensors",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SensorDTO> updateSensor(@RequestBody SensorDTO sensorDTO) throws URISyntaxException {
        log.debug("REST request to update Sensor : {}", sensorDTO);
        if (sensorDTO.getId() == null) {
            return createSensor(sensorDTO);
        }
        SensorDTO result = sensorService.save(sensorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sensorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensors : get all the sensors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sensors in body
     */
    @RequestMapping(value = "/sensors",method = RequestMethod.GET)
    @Timed
    public List<SensorDTO> getAllSensors() {
        log.debug("REST request to get all Sensors");
        return sensorService.findAll();
        }

    /**
     * GET  /sensors/:id : get the "id" sensor.
     *
     * @param id the id of the sensorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensorDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sensors/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SensorDTO> getSensor(@PathVariable Long id) {
        log.debug("REST request to get Sensor : {}", id);
        SensorDTO sensorDTO = sensorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sensorDTO));
    }

    /**
     * DELETE  /sensors/:id : delete the "id" sensor.
     *
     * @param id the id of the sensorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sensors/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        log.debug("REST request to delete Sensor : {}", id);
        sensorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
