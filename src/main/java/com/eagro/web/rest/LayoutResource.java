package com.eagro.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.service.LayoutService;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing Layout.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4400"})
public class LayoutResource {

    private final Logger log = LoggerFactory.getLogger(LayoutResource.class);

    private static final String ENTITY_NAME = "layout";

    @Autowired
    public LayoutService layoutService;


    /**
     * POST  /layouts : Create a new layout.
     *
     * @param layoutDTO the layoutDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new layoutDTO, or with status 400 (Bad Request) if the layout has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/layouts",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<LayoutDTO> createLayout(@RequestBody LayoutDTO layoutDTO) throws URISyntaxException {
        log.debug("REST request to save Layout : {}", layoutDTO);
        if (layoutDTO.getLayoutId() != null) {
            throw new BadRequestAlertException("A new layout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LayoutDTO result = layoutService.save(layoutDTO);
        return ResponseEntity.created(new URI("/api/layouts/" + result.getLayoutId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getLayoutId().toString()))
            .body(result);
    }

    /**
     * PUT  /layouts : Updates an existing layout.
     *
     * @param layoutDTO the layoutDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated layoutDTO,
     * or with status 400 (Bad Request) if the layoutDTO is not valid,
     * or with status 500 (Internal Server Error) if the layoutDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/layouts",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<LayoutDTO> updateLayout(@RequestBody LayoutDTO layoutDTO) throws URISyntaxException {
        log.debug("REST request to update Layout : {}", layoutDTO);
        if (layoutDTO.getLayoutId() == null) {
            return createLayout(layoutDTO);
        }
        LayoutDTO result = layoutService.save(layoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, layoutDTO.getLayoutId().toString()))
            .body(result);
    }

    /**
     * GET  /layouts : get all the layouts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of layouts in body
     */
    @RequestMapping(value = "/layouts",method = RequestMethod.GET)
    @Timed
    public List<LayoutDTO> getAllLayouts() {
        log.debug("REST request to get all Layouts");
        return layoutService.findAll();
        }

    /**
     * GET  /layouts/:id : get the "id" layout.
     *
     * @param id the id of the layoutDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the layoutDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/layouts/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<LayoutDTO> getLayout(@PathVariable Long id) {
        log.debug("REST request to get Layout : {}", id);
        LayoutDTO layoutDTO = layoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(layoutDTO));
    }

    /**
     * DELETE  /layouts/:id : delete the "id" layout.
     *
     * @param id the id of the layoutDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/layouts/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteLayout(@PathVariable Long id) {
        log.debug("REST request to delete Layout : {}", id);
        layoutService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
