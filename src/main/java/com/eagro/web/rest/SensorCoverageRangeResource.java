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
import com.eagro.service.SensorCoverageRangeService;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing SensorCoverageRange.
 */
@RestController
@RequestMapping("/api")
public class SensorCoverageRangeResource {

    private final Logger log = LoggerFactory.getLogger(SensorCoverageRangeResource.class);

    private static final String ENTITY_NAME = "sensorCoverageRange";

    @Autowired
    public SensorCoverageRangeService sensorCoverageRangeService;


    /**
     * POST  /sensor-coverage-ranges : Create a new sensorCoverageRange.
     *
     * @param sensorCoverageRangeDTO the sensorCoverageRangeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sensorCoverageRangeDTO, or with status 400 (Bad Request) if the sensorCoverageRange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensor-coverage-ranges",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SensorCoverageRangeDTO> createSensorCoverageRange(@RequestBody SensorCoverageRangeDTO sensorCoverageRangeDTO) throws URISyntaxException {
        log.debug("REST request to save SensorCoverageRange : {}", sensorCoverageRangeDTO);
        if (sensorCoverageRangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sensorCoverageRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorCoverageRangeDTO result = sensorCoverageRangeService.save(sensorCoverageRangeDTO);
        return ResponseEntity.created(new URI("/api/sensor-coverage-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sensor-coverage-ranges : Updates an existing sensorCoverageRange.
     *
     * @param sensorCoverageRangeDTO the sensorCoverageRangeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sensorCoverageRangeDTO,
     * or with status 400 (Bad Request) if the sensorCoverageRangeDTO is not valid,
     * or with status 500 (Internal Server Error) if the sensorCoverageRangeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sensor-coverage-ranges",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SensorCoverageRangeDTO> updateSensorCoverageRange(@RequestBody SensorCoverageRangeDTO sensorCoverageRangeDTO) throws URISyntaxException {
        log.debug("REST request to update SensorCoverageRange : {}", sensorCoverageRangeDTO);
        if (sensorCoverageRangeDTO.getId() == null) {
            return createSensorCoverageRange(sensorCoverageRangeDTO);
        }
        SensorCoverageRangeDTO result = sensorCoverageRangeService.save(sensorCoverageRangeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sensorCoverageRangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sensor-coverage-ranges : get all the sensorCoverageRanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sensorCoverageRanges in body
     */
    @RequestMapping(value = "/sensor-coverage-ranges",method = RequestMethod.GET)
    @Timed
    public List<SensorCoverageRangeDTO> getAllSensorCoverageRanges() {
        log.debug("REST request to get all SensorCoverageRanges");
        return sensorCoverageRangeService.findAll();
        }

    /**
     * GET  /sensor-coverage-ranges/:id : get the "id" sensorCoverageRange.
     *
     * @param id the id of the sensorCoverageRangeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sensorCoverageRangeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sensor-coverage-ranges/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SensorCoverageRangeDTO> getSensorCoverageRange(@PathVariable Long id) {
        log.debug("REST request to get SensorCoverageRange : {}", id);
        SensorCoverageRangeDTO sensorCoverageRangeDTO = sensorCoverageRangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sensorCoverageRangeDTO));
    }

    /**
     * DELETE  /sensor-coverage-ranges/:id : delete the "id" sensorCoverageRange.
     *
     * @param id the id of the sensorCoverageRangeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sensor-coverage-ranges/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSensorCoverageRange(@PathVariable Long id) {
        log.debug("REST request to delete SensorCoverageRange : {}", id);
        sensorCoverageRangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
