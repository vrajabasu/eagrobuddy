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
import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.service.SectionSensorMappingService;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.SectionSensorMappingMapper;
*//**
 * Test class for the SectionSensorMappingResource REST controller.
 *
 * @see SectionSensorMappingResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class SectionSensorMappingResourceIntTest {

    private static final ZoneType DEFAULT_ZONE_TYPE = ZoneType.WATER;
    private static final ZoneType UPDATED_ZONE_TYPE = ZoneType.ROOT;

    private static final Double DEFAULT_POS_X = 1D;
    private static final Double UPDATED_POS_X = 2D;

    private static final Double DEFAULT_POS_Y = 1D;
    private static final Double UPDATED_POS_Y = 2D;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private SectionSensorMappingRepository sectionSensorMappingRepository;

    @Autowired
    private SectionSensorMappingMapper sectionSensorMappingMapper;

    @Autowired
    private SectionSensorMappingService sectionSensorMappingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionSensorMappingMockMvc;

    private SectionSensorMapping sectionSensorMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionSensorMappingResource sectionSensorMappingResource = new SectionSensorMappingResource(sectionSensorMappingService);
        this.restSectionSensorMappingMockMvc = MockMvcBuilders.standaloneSetup(sectionSensorMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            //.setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static SectionSensorMapping createEntity(EntityManager em) {
        SectionSensorMapping sectionSensorMapping = new SectionSensorMapping()
            .zoneType(DEFAULT_ZONE_TYPE)
            .posX(DEFAULT_POS_X)
            .posY(DEFAULT_POS_Y)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return sectionSensorMapping;
    }

    @Before
    public void initTest() {
        sectionSensorMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createSectionSensorMapping() throws Exception {
        int databaseSizeBeforeCreate = sectionSensorMappingRepository.findAll().size();

        // Create the SectionSensorMapping
        SectionSensorMappingDTO sectionSensorMappingDTO = sectionSensorMappingMapper.toDto(sectionSensorMapping);
        restSectionSensorMappingMockMvc.perform(post("/api/section-sensor-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionSensorMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the SectionSensorMapping in the database
        List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository.findAll();
        assertThat(sectionSensorMappingList).hasSize(databaseSizeBeforeCreate + 1);
        SectionSensorMapping testSectionSensorMapping = sectionSensorMappingList.get(sectionSensorMappingList.size() - 1);
        assertThat(testSectionSensorMapping.getZoneType()).isEqualTo(DEFAULT_ZONE_TYPE);
        assertThat(testSectionSensorMapping.getPosX()).isEqualTo(DEFAULT_POS_X);
        assertThat(testSectionSensorMapping.getPosY()).isEqualTo(DEFAULT_POS_Y);
        assertThat(testSectionSensorMapping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSectionSensorMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSectionSensorMapping.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSectionSensorMapping.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSectionSensorMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionSensorMappingRepository.findAll().size();

        // Create the SectionSensorMapping with an existing ID
        sectionSensorMapping.setId(1L);
        SectionSensorMappingDTO sectionSensorMappingDTO = sectionSensorMappingMapper.toDto(sectionSensorMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionSensorMappingMockMvc.perform(post("/api/section-sensor-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionSensorMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SectionSensorMapping in the database
        List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository.findAll();
        assertThat(sectionSensorMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSectionSensorMappings() throws Exception {
        // Initialize the database
        sectionSensorMappingRepository.saveAndFlush(sectionSensorMapping);

        // Get all the sectionSensorMappingList
        restSectionSensorMappingMockMvc.perform(get("/api/section-sensor-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionSensorMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].zoneType").value(hasItem(DEFAULT_ZONE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].posX").value(hasItem(DEFAULT_POS_X.doubleValue())))
            .andExpect(jsonPath("$.[*].posY").value(hasItem(DEFAULT_POS_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getSectionSensorMapping() throws Exception {
        // Initialize the database
        sectionSensorMappingRepository.saveAndFlush(sectionSensorMapping);

        // Get the sectionSensorMapping
        restSectionSensorMappingMockMvc.perform(get("/api/section-sensor-mappings/{id}", sectionSensorMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sectionSensorMapping.getId().intValue()))
            .andExpect(jsonPath("$.zoneType").value(DEFAULT_ZONE_TYPE.toString()))
            .andExpect(jsonPath("$.posX").value(DEFAULT_POS_X.doubleValue()))
            .andExpect(jsonPath("$.posY").value(DEFAULT_POS_Y.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSectionSensorMapping() throws Exception {
        // Get the sectionSensorMapping
        restSectionSensorMappingMockMvc.perform(get("/api/section-sensor-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSectionSensorMapping() throws Exception {
        // Initialize the database
        sectionSensorMappingRepository.saveAndFlush(sectionSensorMapping);
        int databaseSizeBeforeUpdate = sectionSensorMappingRepository.findAll().size();

        // Update the sectionSensorMapping
        SectionSensorMapping updatedSectionSensorMapping = sectionSensorMappingRepository.findOne(sectionSensorMapping.getId());
        // Disconnect from session so that the updates on updatedSectionSensorMapping are not directly saved in db
        em.detach(updatedSectionSensorMapping);
        updatedSectionSensorMapping
            .zoneType(UPDATED_ZONE_TYPE)
            .posX(UPDATED_POS_X)
            .posY(UPDATED_POS_Y)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        SectionSensorMappingDTO sectionSensorMappingDTO = sectionSensorMappingMapper.toDto(updatedSectionSensorMapping);

        restSectionSensorMappingMockMvc.perform(put("/api/section-sensor-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionSensorMappingDTO)))
            .andExpect(status().isOk());

        // Validate the SectionSensorMapping in the database
        List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository.findAll();
        assertThat(sectionSensorMappingList).hasSize(databaseSizeBeforeUpdate);
        SectionSensorMapping testSectionSensorMapping = sectionSensorMappingList.get(sectionSensorMappingList.size() - 1);
        assertThat(testSectionSensorMapping.getZoneType()).isEqualTo(UPDATED_ZONE_TYPE);
        assertThat(testSectionSensorMapping.getPosX()).isEqualTo(UPDATED_POS_X);
        assertThat(testSectionSensorMapping.getPosY()).isEqualTo(UPDATED_POS_Y);
        assertThat(testSectionSensorMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSectionSensorMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSectionSensorMapping.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSectionSensorMapping.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSectionSensorMapping() throws Exception {
        int databaseSizeBeforeUpdate = sectionSensorMappingRepository.findAll().size();

        // Create the SectionSensorMapping
        SectionSensorMappingDTO sectionSensorMappingDTO = sectionSensorMappingMapper.toDto(sectionSensorMapping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionSensorMappingMockMvc.perform(put("/api/section-sensor-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionSensorMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the SectionSensorMapping in the database
        List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository.findAll();
        assertThat(sectionSensorMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSectionSensorMapping() throws Exception {
        // Initialize the database
        sectionSensorMappingRepository.saveAndFlush(sectionSensorMapping);
        int databaseSizeBeforeDelete = sectionSensorMappingRepository.findAll().size();

        // Get the sectionSensorMapping
        restSectionSensorMappingMockMvc.perform(delete("/api/section-sensor-mappings/{id}", sectionSensorMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SectionSensorMapping> sectionSensorMappingList = sectionSensorMappingRepository.findAll();
        assertThat(sectionSensorMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionSensorMapping.class);
        SectionSensorMapping sectionSensorMapping1 = new SectionSensorMapping();
        sectionSensorMapping1.setId(1L);
        SectionSensorMapping sectionSensorMapping2 = new SectionSensorMapping();
        sectionSensorMapping2.setId(sectionSensorMapping1.getId());
        assertThat(sectionSensorMapping1).isEqualTo(sectionSensorMapping2);
        sectionSensorMapping2.setId(2L);
        assertThat(sectionSensorMapping1).isNotEqualTo(sectionSensorMapping2);
        sectionSensorMapping1.setId(null);
        assertThat(sectionSensorMapping1).isNotEqualTo(sectionSensorMapping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionSensorMappingDTO.class);
        SectionSensorMappingDTO sectionSensorMappingDTO1 = new SectionSensorMappingDTO();
        sectionSensorMappingDTO1.setId(1L);
        SectionSensorMappingDTO sectionSensorMappingDTO2 = new SectionSensorMappingDTO();
        assertThat(sectionSensorMappingDTO1).isNotEqualTo(sectionSensorMappingDTO2);
        sectionSensorMappingDTO2.setId(sectionSensorMappingDTO1.getId());
        assertThat(sectionSensorMappingDTO1).isEqualTo(sectionSensorMappingDTO2);
        sectionSensorMappingDTO2.setId(2L);
        assertThat(sectionSensorMappingDTO1).isNotEqualTo(sectionSensorMappingDTO2);
        sectionSensorMappingDTO1.setId(null);
        assertThat(sectionSensorMappingDTO1).isNotEqualTo(sectionSensorMappingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sectionSensorMappingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sectionSensorMappingMapper.fromId(null)).isNull();
    }
}
*/