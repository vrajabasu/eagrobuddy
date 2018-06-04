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
import com.eagro.service.SensorDataService;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing SensorData.
 */
@RestController
@RequestMapping("/api")
public class SensorDataWebService {

    private final Logger log = LoggerFactory.getLogger(SensorDataWebService.class);

    private static final String ENTITY_NAME = "sensorData";

    @Autowired
    public SensorDataService sensorDataService;


    /**
     * POST  /sensor-data : Create a new sensorData.
     *
     * @param sensorDataDTO the sensorDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensorDataDTO, or with status 400 (Bad Request) if the sensorData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensor-data",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SensorDataDTO> createSensorData(@RequestBody SensorDataDTO sensorDataDTO) throws URISyntaxException {
        log.debug("REST request to save SensorData : {}", sensorDataDTO);
        if (sensorDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new sensorData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorDataDTO result = sensorDataService.save(sensorDataDTO);
        return ResponseEntity.created(new URI("/api/sensor-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensor-data : Updates an existing sensorData.
     *
     * @param sensorDataDTO the sensorDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensorDataDTO,
     * or with status 400 (Bad Request) if the sensorDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the sensorDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensor-data",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SensorDataDTO> updateSensorData(@RequestBody SensorDataDTO sensorDataDTO) throws URISyntaxException {
        log.debug("REST request to update SensorData : {}", sensorDataDTO);
        if (sensorDataDTO.getId() == null) {
            return createSensorData(sensorDataDTO);
        }
        SensorDataDTO result = sensorDataService.save(sensorDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sensorDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensor-data : get all the sensorData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sensorData in body
     */
    @RequestMapping(value = "/sensor-data",method = RequestMethod.GET)
    @Timed
    public List<SensorDataDTO> getAllSensorData() {
        log.debug("REST request to get all SensorData");
        return sensorDataService.findAll();
        }

    /**
     * GET  /sensor-data/:id : get the "id" sensorData.
     *
     * @param id the id of the sensorDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensorDataDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sensor-data/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SensorDataDTO> getSensorData(@PathVariable Long id) {
        log.debug("REST request to get SensorData : {}", id);
        SensorDataDTO sensorDataDTO = sensorDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sensorDataDTO));
    }

    /**
     * DELETE  /sensor-data/:id : delete the "id" sensorData.
     *
     * @param id the id of the sensorDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sensor-data/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSensorData(@PathVariable Long id) {
        log.debug("REST request to delete SensorData : {}", id);
        sensorDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
