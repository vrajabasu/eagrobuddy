package com.eagro.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.config.Constants;
import com.eagro.entities.User;
import com.eagro.repository.UserRepository;
import com.eagro.security.AuthoritiesConstants;
import com.eagro.service.UserService;
import com.eagro.service.dto.UserDTO;
import com.eagro.service.exception.BadRequestAlertException;
import com.eagro.service.exception.EmailAlreadyUsedException;
import com.eagro.service.exception.LoginAlreadyUsedException;
import com.eagro.service.utils.HeaderUtil;
import com.eagro.service.utils.PaginationUtil;
import com.eagro.service.utils.ResponseUtil;

/**
 * REST controller for managing EagroUser.
 */
@RestController
@RequestMapping("/api")
public class UserWebService {

    private final Logger log = LoggerFactory.getLogger(UserWebService.class);

    @Autowired
    public UserService userService;
    
    @Autowired
    public UserRepository userRepository;

    

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws BadRequestAlertException 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getUserId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByloginKey(userDTO.getLoginKey().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
           // mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLoginKey()))
                .headers(HeaderUtil.createAlert( "userManagement.created", newUser.getLoginKey()))
                .body(newUser);
        }
    }

    /**
     * PUT /users : Updates an existing User.
     *
     * @param userDTO the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already in use
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already in use
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailAddressIgnoreCase(userDTO.getEmailAddress());
        if (existingUser.isPresent() && (!existingUser.get().getUserId().equals(userDTO.getUserId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByloginKey(userDTO.getLoginKey().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getUserId().equals(userDTO.getUserId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userDTO.getLoginKey()));
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "userManagement.deleted", login)).build();
    }
    

  /*  *//**
     * POST  /eagro-users : Create a new eagroUser.
     *
     * @param eagroUserDTO the eagroUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eagroUserDTO, or with status 400 (Bad Request) if the eagroUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    @Timed
    public ResponseEntity<UserDTO> createEagroUser(@RequestBody UserDTO eagroUserDTO) throws URISyntaxException {
        log.debug("REST request to save EagroUser : {}", eagroUserDTO);
        if (eagroUserDTO.getUserId() != null) {
            throw new BadRequestAlertException("A new eagroUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDTO result = userService.save(eagroUserDTO);
        return ResponseEntity.created(new URI("/api/eagro-users/" + result.getUserId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getUserId().toString()))
            .body(result);
    }

    *//**
     * PUT  /eagro-users : Updates an existing eagroUser.
     *
     * @param eagroUserDTO the eagroUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eagroUserDTO,
     * or with status 400 (Bad Request) if the eagroUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the eagroUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    @Timed
    public ResponseEntity<UserDTO> updateEagroUser(@RequestBody UserDTO eagroUserDTO) throws URISyntaxException {
        log.debug("REST request to update EagroUser : {}", eagroUserDTO);
        if (eagroUserDTO.getUserId() == null) {
            return createEagroUser(eagroUserDTO);
        }
        UserDTO result = userService.save(eagroUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eagroUserDTO.getUserId().toString()))
            .body(result);
    }

    *//**
     * GET  /eagro-users : get all the eagroUsers.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of eagroUsers in body
     *//*
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @Timed
    public List<UserDTO> getAllEagroUsers(@RequestParam(required = false) String filter) {
        if ("userrole-is-null".equals(filter)) {
            log.debug("REST request to get all EagroUsers where userRole is null");
            return eagroUserService.findAllWhereUserRoleIsNull();
        }
        log.debug("REST request to get all EagroUsers");
        return userService.findAll();
        }

    *//**
     * GET  /eagro-users/:id : get the "id" eagroUser.
     *
     * @param id the id of the eagroUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eagroUserDTO, or with status 404 (Not Found)
     *//*
    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    @Timed
    public ResponseEntity<UserDTO> getEagroUser(@PathVariable Long id) {
        log.debug("REST request to get EagroUser : {}", id);
        UserDTO eagroUserDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eagroUserDTO));
    }

    *//**
     * DELETE  /eagro-users/:id : delete the "id" eagroUser.
     *
     * @param id the id of the eagroUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     *//*
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    @Timed
    public ResponseEntity<Void> deleteEagroUser(@PathVariable Long id) {
        log.debug("REST request to delete EagroUser : {}", id);
        userService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/
}
