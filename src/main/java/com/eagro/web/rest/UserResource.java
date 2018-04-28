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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.service.UserService;
import com.eagro.service.dto.UserDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing EagroUser.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "eagroUser";

    @Autowired
    public UserService eagroUserService;

    /**
     * POST  /eagro-users : Create a new eagroUser.
     *
     * @param eagroUserDTO the eagroUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eagroUserDTO, or with status 400 (Bad Request) if the eagroUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<UserDTO> createEagroUser(@RequestBody UserDTO eagroUserDTO) throws URISyntaxException {
        log.debug("REST request to save EagroUser : {}", eagroUserDTO);
        if (eagroUserDTO.getUserId() != null) {
            throw new BadRequestAlertException("A new eagroUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDTO result = eagroUserService.save(eagroUserDTO);
        return ResponseEntity.created(new URI("/api/eagro-users/" + result.getUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getUserId().toString()))
            .body(result);
    }

    /**
     * PUT  /eagro-users : Updates an existing eagroUser.
     *
     * @param eagroUserDTO the eagroUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eagroUserDTO,
     * or with status 400 (Bad Request) if the eagroUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the eagroUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<UserDTO> updateEagroUser(@RequestBody UserDTO eagroUserDTO) throws URISyntaxException {
        log.debug("REST request to update EagroUser : {}", eagroUserDTO);
        if (eagroUserDTO.getUserId() == null) {
            return createEagroUser(eagroUserDTO);
        }
        UserDTO result = eagroUserService.save(eagroUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eagroUserDTO.getUserId().toString()))
            .body(result);
    }

    /**
     * GET  /eagro-users : get all the eagroUsers.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of eagroUsers in body
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @Timed
    public List<UserDTO> getAllEagroUsers(@RequestParam(required = false) String filter) {
        if ("userrole-is-null".equals(filter)) {
            log.debug("REST request to get all EagroUsers where userRole is null");
            return eagroUserService.findAllWhereUserRoleIsNull();
        }
        log.debug("REST request to get all EagroUsers");
        return eagroUserService.findAll();
        }

    /**
     * GET  /eagro-users/:id : get the "id" eagroUser.
     *
     * @param id the id of the eagroUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eagroUserDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<UserDTO> getEagroUser(@PathVariable Long id) {
        log.debug("REST request to get EagroUser : {}", id);
        UserDTO eagroUserDTO = eagroUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eagroUserDTO));
    }

    /**
     * DELETE  /eagro-users/:id : delete the "id" eagroUser.
     *
     * @param id the id of the eagroUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteEagroUser(@PathVariable Long id) {
        log.debug("REST request to delete EagroUser : {}", id);
        eagroUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
