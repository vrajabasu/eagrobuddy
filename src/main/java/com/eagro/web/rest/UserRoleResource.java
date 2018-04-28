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
import com.eagro.service.UserRoleService;
import com.eagro.service.dto.UserRoleDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing UserRole.
 */
@RestController
@RequestMapping("/api")
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private static final String ENTITY_NAME = "userRole";

    @Autowired
    public UserRoleService userRoleService;


    /**
     * POST  /user-roles : Create a new userRole.
     *
     * @param userRoleDTO the userRoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userRoleDTO, or with status 400 (Bad Request) if the userRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-roles",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to save UserRole : {}", userRoleDTO);
        if (userRoleDTO.getRoleId() != null) {
            throw new BadRequestAlertException("A new userRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.created(new URI("/api/user-roles/" + result.getRoleId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getRoleId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-roles : Updates an existing userRole.
     *
     * @param userRoleDTO the userRoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userRoleDTO,
     * or with status 400 (Bad Request) if the userRoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the userRoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/user-roles",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<UserRoleDTO> updateUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to update UserRole : {}", userRoleDTO);
        if (userRoleDTO.getRoleId() == null) {
            return createUserRole(userRoleDTO);
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userRoleDTO.getRoleId().toString()))
            .body(result);
    }

    /**
     * GET  /user-roles : get all the userRoles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userRoles in body
     */
    @RequestMapping(value = "/user-roles",method = RequestMethod.GET)
    @Timed
    public List<UserRoleDTO> getAllUserRoles() {
        log.debug("REST request to get all UserRoles");
        return userRoleService.findAll();
        }

    /**
     * GET  /user-roles/:id : get the "id" userRole.
     *
     * @param id the id of the userRoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userRoleDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/user-roles/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<UserRoleDTO> getUserRole(@PathVariable Long id) {
        log.debug("REST request to get UserRole : {}", id);
        UserRoleDTO userRoleDTO = userRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userRoleDTO));
    }

    /**
     * DELETE  /user-roles/:id : delete the "id" userRole.
     *
     * @param id the id of the userRoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/user-roles/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        log.debug("REST request to delete UserRole : {}", id);
        userRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
