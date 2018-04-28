/*package com.eagro.web.rest;

import static com.eagro.web.rest.TestUtil.createFormattingConversionService;
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
import com.eagro.entities.UserLayoutMapping;
import com.eagro.repository.UserLayoutMappingRepository;
import com.eagro.service.UserLayoutMappingService;
import com.eagro.service.dto.UserLayoutMappingDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.UserLayoutMappingMapper;

*//**
 * Test class for the UserLayoutMappingResource REST controller.
 *
 * @see UserLayoutMappingResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class UserLayoutMappingResourceIntTest {

    private static final String DEFAULT_ACTIVE_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_FLAG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private UserLayoutMappingRepository userLayoutMappingRepository;

    @Autowired
    private UserLayoutMappingMapper userLayoutMappingMapper;

    @Autowired
    private UserLayoutMappingService userLayoutMappingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserLayoutMappingMockMvc;

    private UserLayoutMapping userLayoutMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserLayoutMappingResource userLayoutMappingResource = new UserLayoutMappingResource(userLayoutMappingService);
        this.restUserLayoutMappingMockMvc = MockMvcBuilders.standaloneSetup(userLayoutMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
           // .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static UserLayoutMapping createEntity(EntityManager em) {
        UserLayoutMapping userLayoutMapping = new UserLayoutMapping()
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return userLayoutMapping;
    }

    @Before
    public void initTest() {
        userLayoutMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserLayoutMapping() throws Exception {
        int databaseSizeBeforeCreate = userLayoutMappingRepository.findAll().size();

        // Create the UserLayoutMapping
        UserLayoutMappingDTO userLayoutMappingDTO = userLayoutMappingMapper.toDto(userLayoutMapping);
        restUserLayoutMappingMockMvc.perform(post("/api/user-layout-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLayoutMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserLayoutMapping in the database
        List<UserLayoutMapping> userLayoutMappingList = userLayoutMappingRepository.findAll();
        assertThat(userLayoutMappingList).hasSize(databaseSizeBeforeCreate + 1);
        UserLayoutMapping testUserLayoutMapping = userLayoutMappingList.get(userLayoutMappingList.size() - 1);
        assertThat(testUserLayoutMapping.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testUserLayoutMapping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testUserLayoutMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testUserLayoutMapping.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testUserLayoutMapping.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createUserLayoutMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userLayoutMappingRepository.findAll().size();

        // Create the UserLayoutMapping with an existing ID
        userLayoutMapping.setId(1L);
        UserLayoutMappingDTO userLayoutMappingDTO = userLayoutMappingMapper.toDto(userLayoutMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLayoutMappingMockMvc.perform(post("/api/user-layout-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLayoutMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserLayoutMapping in the database
        List<UserLayoutMapping> userLayoutMappingList = userLayoutMappingRepository.findAll();
        assertThat(userLayoutMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserLayoutMappings() throws Exception {
        // Initialize the database
        userLayoutMappingRepository.saveAndFlush(userLayoutMapping);

        // Get all the userLayoutMappingList
        restUserLayoutMappingMockMvc.perform(get("/api/user-layout-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLayoutMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getUserLayoutMapping() throws Exception {
        // Initialize the database
        userLayoutMappingRepository.saveAndFlush(userLayoutMapping);

        // Get the userLayoutMapping
        restUserLayoutMappingMockMvc.perform(get("/api/user-layout-mappings/{id}", userLayoutMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userLayoutMapping.getId().intValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserLayoutMapping() throws Exception {
        // Get the userLayoutMapping
        restUserLayoutMappingMockMvc.perform(get("/api/user-layout-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserLayoutMapping() throws Exception {
        // Initialize the database
        userLayoutMappingRepository.saveAndFlush(userLayoutMapping);
        int databaseSizeBeforeUpdate = userLayoutMappingRepository.findAll().size();

        // Update the userLayoutMapping
        UserLayoutMapping updatedUserLayoutMapping = userLayoutMappingRepository.findOne(userLayoutMapping.getId());
        // Disconnect from session so that the updates on updatedUserLayoutMapping are not directly saved in db
        em.detach(updatedUserLayoutMapping);
        updatedUserLayoutMapping
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        UserLayoutMappingDTO userLayoutMappingDTO = userLayoutMappingMapper.toDto(updatedUserLayoutMapping);

        restUserLayoutMappingMockMvc.perform(put("/api/user-layout-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLayoutMappingDTO)))
            .andExpect(status().isOk());

        // Validate the UserLayoutMapping in the database
        List<UserLayoutMapping> userLayoutMappingList = userLayoutMappingRepository.findAll();
        assertThat(userLayoutMappingList).hasSize(databaseSizeBeforeUpdate);
        UserLayoutMapping testUserLayoutMapping = userLayoutMappingList.get(userLayoutMappingList.size() - 1);
        assertThat(testUserLayoutMapping.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testUserLayoutMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testUserLayoutMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testUserLayoutMapping.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testUserLayoutMapping.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserLayoutMapping() throws Exception {
        int databaseSizeBeforeUpdate = userLayoutMappingRepository.findAll().size();

        // Create the UserLayoutMapping
        UserLayoutMappingDTO userLayoutMappingDTO = userLayoutMappingMapper.toDto(userLayoutMapping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserLayoutMappingMockMvc.perform(put("/api/user-layout-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLayoutMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserLayoutMapping in the database
        List<UserLayoutMapping> userLayoutMappingList = userLayoutMappingRepository.findAll();
        assertThat(userLayoutMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserLayoutMapping() throws Exception {
        // Initialize the database
        userLayoutMappingRepository.saveAndFlush(userLayoutMapping);
        int databaseSizeBeforeDelete = userLayoutMappingRepository.findAll().size();

        // Get the userLayoutMapping
        restUserLayoutMappingMockMvc.perform(delete("/api/user-layout-mappings/{id}", userLayoutMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserLayoutMapping> userLayoutMappingList = userLayoutMappingRepository.findAll();
        assertThat(userLayoutMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLayoutMapping.class);
        UserLayoutMapping userLayoutMapping1 = new UserLayoutMapping();
        userLayoutMapping1.setId(1L);
        UserLayoutMapping userLayoutMapping2 = new UserLayoutMapping();
        userLayoutMapping2.setId(userLayoutMapping1.getId());
        assertThat(userLayoutMapping1).isEqualTo(userLayoutMapping2);
        userLayoutMapping2.setId(2L);
        assertThat(userLayoutMapping1).isNotEqualTo(userLayoutMapping2);
        userLayoutMapping1.setId(null);
        assertThat(userLayoutMapping1).isNotEqualTo(userLayoutMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLayoutMappingDTO.class);
        UserLayoutMappingDTO userLayoutMappingDTO1 = new UserLayoutMappingDTO();
        userLayoutMappingDTO1.setId(1L);
        UserLayoutMappingDTO userLayoutMappingDTO2 = new UserLayoutMappingDTO();
        assertThat(userLayoutMappingDTO1).isNotEqualTo(userLayoutMappingDTO2);
        userLayoutMappingDTO2.setId(userLayoutMappingDTO1.getId());
        assertThat(userLayoutMappingDTO1).isEqualTo(userLayoutMappingDTO2);
        userLayoutMappingDTO2.setId(2L);
        assertThat(userLayoutMappingDTO1).isNotEqualTo(userLayoutMappingDTO2);
        userLayoutMappingDTO1.setId(null);
        assertThat(userLayoutMappingDTO1).isNotEqualTo(userLayoutMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userLayoutMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userLayoutMappingMapper.fromId(null)).isNull();
    }
}
*/