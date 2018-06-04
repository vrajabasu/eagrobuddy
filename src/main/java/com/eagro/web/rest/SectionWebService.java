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
import com.eagro.service.SectionService;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing Section.
 */
@RestController
@RequestMapping("/api")
public class SectionWebService {

    private final Logger log = LoggerFactory.getLogger(SectionWebService.class);

    private static final String ENTITY_NAME = "section";

    @Autowired
    public SectionService sectionService;


    /**
     * POST  /sections : Create a new section.
     *
     * @param sectionDTO the sectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectionDTO, or with status 400 (Bad Request) if the section has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sections",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SectionDTO> createSection(@RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to save Section : {}", sectionDTO);
        if (sectionDTO.getSectionId() != null) {
            throw new BadRequestAlertException("A new section cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.created(new URI("/api/sections/" + result.getSectionId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getSectionId().toString()))
            .body(result);
    }

    /**
     * PUT  /sections : Updates an existing section.
     *
     * @param sectionDTO the sectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectionDTO,
     * or with status 400 (Bad Request) if the sectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the sectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/sections",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<SectionDTO> updateSection(@RequestBody SectionDTO sectionDTO) throws URISyntaxException {
        log.debug("REST request to update Section : {}", sectionDTO);
        if (sectionDTO.getSectionId() == null) {
            return createSection(sectionDTO);
        }
        SectionDTO result = sectionService.save(sectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectionDTO.getSectionId().toString()))
            .body(result);
    }

    /**
     * GET  /sections : get all the sections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sections in body
     */
    @RequestMapping(value = "/sections",method = RequestMethod.GET)
    @Timed
    public List<SectionDTO> getAllSections() {
        log.debug("REST request to get all Sections");
        return sectionService.findAll();
        }

    /**
     * GET  /sections/:id : get the "id" section.
     *
     * @param id the id of the sectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectionDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/sections/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<SectionDTO> getSection(@PathVariable Long id) {
        log.debug("REST request to get Section : {}", id);
        SectionDTO sectionDTO = sectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectionDTO));
    }

    /**
     * DELETE  /sections/:id : delete the "id" section.
     *
     * @param id the id of the sectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/sections/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        log.debug("REST request to delete Section : {}", id);
        sectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
