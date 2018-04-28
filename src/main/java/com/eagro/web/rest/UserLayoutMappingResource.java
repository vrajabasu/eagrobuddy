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
import com.eagro.service.UserLayoutMappingService;
import com.eagro.service.dto.UserLayoutMappingDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing UserLayoutMapping.
 */
@RestController
@RequestMapping("/api")
public class UserLayoutMappingResource {

    private final Logger log = LoggerFactory.getLogger(UserLayoutMappingResource.class);

    private static final String ENTITY_NAME = "userLayoutMapping";

    @Autowired
    public UserLayoutMappingService userLayoutMappingService;

    /**
     * POST  /user-layout-mappings : Create a new userLayoutMapping.
     *
     * @param userLayoutMappingDTO the userLayoutMappingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userLayoutMappingDTO, or with status 400 (Bad Request) if the userLayoutMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-layout-mappings",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<UserLayoutMappingDTO> createUserLayoutMapping(@RequestBody UserLayoutMappingDTO userLayoutMappingDTO) throws URISyntaxException {
        log.debug("REST request to save UserLayoutMapping : {}", userLayoutMappingDTO);
        if (userLayoutMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new userLayoutMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserLayoutMappingDTO result = userLayoutMappingService.save(userLayoutMappingDTO);
        return ResponseEntity.created(new URI("/api/user-layout-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-layout-mappings : Updates an existing userLayoutMapping.
     *
     * @param userLayoutMappingDTO the userLayoutMappingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userLayoutMappingDTO,
     * or with status 400 (Bad Request) if the userLayoutMappingDTO is not valid,
     * or with status 500 (Internal Server Error) if the userLayoutMappingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-layout-mappings",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<UserLayoutMappingDTO> updateUserLayoutMapping(@RequestBody UserLayoutMappingDTO userLayoutMappingDTO) throws URISyntaxException {
        log.debug("REST request to update UserLayoutMapping : {}", userLayoutMappingDTO);
        if (userLayoutMappingDTO.getId() == null) {
            return createUserLayoutMapping(userLayoutMappingDTO);
        }
        UserLayoutMappingDTO result = userLayoutMappingService.save(userLayoutMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userLayoutMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-layout-mappings : get all the userLayoutMappings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userLayoutMappings in body
     */
    @RequestMapping(value = "/user-layout-mappings",method = RequestMethod.GET)
    @Timed
    public List<UserLayoutMappingDTO> getAllUserLayoutMappings() {
        log.debug("REST request to get all UserLayoutMappings");
        return userLayoutMappingService.findAll();
        }

    /**
     * GET  /user-layout-mappings/:id : get the "id" userLayoutMapping.
     *
     * @param id the id of the userLayoutMappingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userLayoutMappingDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-layout-mappings/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<UserLayoutMappingDTO> getUserLayoutMapping(@PathVariable Long id) {
        log.debug("REST request to get UserLayoutMapping : {}", id);
        UserLayoutMappingDTO userLayoutMappingDTO = userLayoutMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userLayoutMappingDTO));
    }

    /**
     * DELETE  /user-layout-mappings/:id : delete the "id" userLayoutMapping.
     *
     * @param id the id of the userLayoutMappingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-layout-mappings/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteUserLayoutMapping(@PathVariable Long id) {
        log.debug("REST request to delete UserLayoutMapping : {}", id);
        userLayoutMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
