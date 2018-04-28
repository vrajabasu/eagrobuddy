/*package com.eagro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.EagroserviceApplication;
import com.eagro.entities.User;
import com.eagro.repository.UserRepository;
import com.eagro.service.UserService;
import com.eagro.service.dto.UserDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.UserMapper;

*//**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class UserResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_LOGIN_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final boolean DEFAULT_ACTIVE_FLAG = Boolean.FALSE;
    private static final boolean UPDATED_ACTIVE_FLAG = Boolean.TRUE;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private UserMapper UserMapper;

    @Autowired
    private UserService UserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMockMvc;

    private User User;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource UserResource = new UserResource(UserService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(UserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
           // .setControllerAdvice(exceptionTranslator)
          //  .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static User createEntity(EntityManager em) {
        User User = new User()
            .userId(DEFAULT_USER_ID)
            .loginKey(DEFAULT_LOGIN_KEY)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return User;
    }

    @Before
    public void initTest() {
        User = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = UserRepository.findAll().size();

        // Create the User
        UserDTO UserDTO = UserMapper.toDto(User);
        restUserMockMvc.perform(post("/api/eagro-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(UserDTO)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> UserList = UserRepository.findAll();
        assertThat(UserList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = UserList.get(UserList.size() - 1);
        assertThat(testUser.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUser.getLoginKey()).isEqualTo(DEFAULT_LOGIN_KEY);
        assertThat(testUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUser.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUser.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testUser.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testUser.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUser.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUser.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = UserRepository.findAll().size();

        // Create the User with an existing ID
        User.setId(1L);
        UserDTO UserDTO = UserMapper.toDto(User);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post("/api/eagro-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(UserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> UserList = UserRepository.findAll();
        assertThat(UserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        UserRepository.saveAndFlush(User);

        // Get all the UserList
        restUserMockMvc.perform(get("/api/eagro-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(User.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].loginKey").value(hasItem(DEFAULT_LOGIN_KEY.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        UserRepository.saveAndFlush(User);

        // Get the User
        restUserMockMvc.perform(get("/api/eagro-users/{id}", User.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(User.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.loginKey").value(DEFAULT_LOGIN_KEY.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        // Get the User
        restUserMockMvc.perform(get("/api/eagro-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        UserRepository.saveAndFlush(User);
        int databaseSizeBeforeUpdate = UserRepository.findAll().size();

        // Update the User
        User updatedUser = UserRepository.findOne(User.getId());
        // Disconnect from session so that the updates on updatedUser are not directly saved in db
        em.detach(updatedUser);
        updatedUser
            .userId(UPDATED_USER_ID)
            .loginKey(UPDATED_LOGIN_KEY)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        UserDTO UserDTO = UserMapper.toDto(updatedUser);

        restUserMockMvc.perform(put("/api/eagro-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(UserDTO)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> UserList = UserRepository.findAll();
        assertThat(UserList).hasSize(databaseSizeBeforeUpdate);
        User testUser = UserList.get(UserList.size() - 1);
        assertThat(testUser.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUser.getLoginKey()).isEqualTo(UPDATED_LOGIN_KEY);
        assertThat(testUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUser.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUser.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testUser.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testUser.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUser.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUser.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingUser() throws Exception {
        int databaseSizeBeforeUpdate = UserRepository.findAll().size();

        // Create the User
        UserDTO UserDTO = UserMapper.toDto(User);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMockMvc.perform(put("/api/eagro-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(UserDTO)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> UserList = UserRepository.findAll();
        assertThat(UserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        UserRepository.saveAndFlush(User);
        int databaseSizeBeforeDelete = UserRepository.findAll().size();

        // Get the User
        restUserMockMvc.perform(delete("/api/eagro-users/{id}", User.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User> UserList = UserRepository.findAll();
        assertThat(UserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User.class);
        User User1 = new User();
        User1.setId(1L);
        User User2 = new User();
        User2.setId(User1.getId());
        assertThat(User1).isEqualTo(User2);
        User2.setId(2L);
        assertThat(User1).isNotEqualTo(User2);
        User1.setId(null);
        assertThat(User1).isNotEqualTo(User2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDTO.class);
        UserDTO UserDTO1 = new UserDTO();
        UserDTO1.setId(1L);
        UserDTO UserDTO2 = new UserDTO();
        assertThat(UserDTO1).isNotEqualTo(UserDTO2);
        UserDTO2.setId(UserDTO1.getId());
        assertThat(UserDTO1).isEqualTo(UserDTO2);
        UserDTO2.setId(2L);
        assertThat(UserDTO1).isNotEqualTo(UserDTO2);
        UserDTO1.setId(null);
        assertThat(UserDTO1).isNotEqualTo(UserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(UserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(UserMapper.fromId(null)).isNull();
    }
}
*/