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
import com.eagro.service.SectionSensorMappingService;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing SectionSensorMapping.
 */
@RestController
@RequestMapping("/api")
public class SectionSensorMappingWebService {

    private final Logger log = LoggerFactory.getLogger(SectionSensorMappingWebService.class);

    private static final String ENTITY_NAME = "sectionSensorMapping";

    @Autowired
    public SectionSensorMappingService sectionSensorMappingService;

    /**
     * POST  /section-sensor-mappings : Create a new sectionSensorMapping.
     *
     * @param sectionSensorMappingDTO the sectionSensorMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionSensorMappingDTO, or with status 400 (Bad Request) if the sectionSensorMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/section-sensor-mappings",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SectionSensorMappingDTO> createSectionSensorMapping(@RequestBody SectionSensorMappingDTO sectionSensorMappingDTO) throws URISyntaxException {
        log.debug("REST request to save SectionSensorMapping : {}", sectionSensorMappingDTO);
        if (sectionSensorMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new sectionSensorMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionSensorMappingDTO result = sectionSensorMappingService.save(sectionSensorMappingDTO);
        return ResponseEntity.created(new URI("/api/section-sensor-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /section-sensor-mappings : Updates an existing sectionSensorMapping.
     *
     * @param sectionSensorMappingDTO the sectionSensorMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionSensorMappingDTO,
     * or with status 400 (Bad Request) if the sectionSensorMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the sectionSensorMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/section-sensor-mappings",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SectionSensorMappingDTO> updateSectionSensorMapping(@RequestBody SectionSensorMappingDTO sectionSensorMappingDTO) throws URISyntaxException {
        log.debug("REST request to update SectionSensorMapping : {}", sectionSensorMappingDTO);
        if (sectionSensorMappingDTO.getId() == null) {
            return createSectionSensorMapping(sectionSensorMappingDTO);
        }
        SectionSensorMappingDTO result = sectionSensorMappingService.save(sectionSensorMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionSensorMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /section-sensor-mappings : get all the sectionSensorMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sectionSensorMappings in body
     */
    @RequestMapping(value = "/section-sensor-mappings",method = RequestMethod.GET)
    @Timed
    public List<SectionSensorMappingDTO> getAllSectionSensorMappings() {
        log.debug("REST request to get all SectionSensorMappings");
        return sectionSensorMappingService.findAll();
        }

    /**
     * GET  /section-sensor-mappings/:id : get the "id" sectionSensorMapping.
     *
     * @param id the id of the sectionSensorMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionSensorMappingDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/section-sensor-mappings/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SectionSensorMappingDTO> getSectionSensorMapping(@PathVariable Long id) {
        log.debug("REST request to get SectionSensorMapping : {}", id);
        SectionSensorMappingDTO sectionSensorMappingDTO = sectionSensorMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionSensorMappingDTO));
    }

    /**
     * DELETE  /section-sensor-mappings/:id : delete the "id" sectionSensorMapping.
     *
     * @param id the id of the sectionSensorMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/section-sensor-mappings/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSectionSensorMapping(@PathVariable Long id) {
        log.debug("REST request to delete SectionSensorMapping : {}", id);
        sectionSensorMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
