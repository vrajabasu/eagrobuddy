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
import com.eagro.entities.SensorCoverageRange;
import com.eagro.repository.SensorCoverageRangeRepository;
import com.eagro.service.SensorCoverageRangeService;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.SensorCoverageRangeMapper;

*//**
 * Test class for the SensorCoverageRangeResource REST controller.
 *
 * @see SensorCoverageRangeResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class SensorCoverageRangeResourceIntTest {

    private static final Double DEFAULT_START_X = 1D;
    private static final Double UPDATED_START_X = 2D;

    private static final Double DEFAULT_START_Y = 1D;
    private static final Double UPDATED_START_Y = 2D;

    private static final Double DEFAULT_END_X = 1D;
    private static final Double UPDATED_END_X = 2D;

    private static final Double DEFAULT_END_Y = 1D;
    private static final Double UPDATED_END_Y = 2D;

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
    private SensorCoverageRangeRepository sensorCoverageRangeRepository;

    @Autowired
    private SensorCoverageRangeMapper sensorCoverageRangeMapper;

    @Autowired
    private SensorCoverageRangeService sensorCoverageRangeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSensorCoverageRangeMockMvc;

    private SensorCoverageRange sensorCoverageRange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SensorCoverageRangeResource sensorCoverageRangeResource = new SensorCoverageRangeResource(sensorCoverageRangeService);
        this.restSensorCoverageRangeMockMvc = MockMvcBuilders.standaloneSetup(sensorCoverageRangeResource)
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
    public static SensorCoverageRange createEntity(EntityManager em) {
        SensorCoverageRange sensorCoverageRange = new SensorCoverageRange()
            .startX(DEFAULT_START_X)
            .startY(DEFAULT_START_Y)
            .endX(DEFAULT_END_X)
            .endY(DEFAULT_END_Y)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return sensorCoverageRange;
    }

    @Before
    public void initTest() {
        sensorCoverageRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createSensorCoverageRange() throws Exception {
        int databaseSizeBeforeCreate = sensorCoverageRangeRepository.findAll().size();

        // Create the SensorCoverageRange
        SensorCoverageRangeDTO sensorCoverageRangeDTO = sensorCoverageRangeMapper.toDto(sensorCoverageRange);
        restSensorCoverageRangeMockMvc.perform(post("/api/sensor-coverage-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorCoverageRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the SensorCoverageRange in the database
        List<SensorCoverageRange> sensorCoverageRangeList = sensorCoverageRangeRepository.findAll();
        assertThat(sensorCoverageRangeList).hasSize(databaseSizeBeforeCreate + 1);
        SensorCoverageRange testSensorCoverageRange = sensorCoverageRangeList.get(sensorCoverageRangeList.size() - 1);
        assertThat(testSensorCoverageRange.getStartX()).isEqualTo(DEFAULT_START_X);
        assertThat(testSensorCoverageRange.getStartY()).isEqualTo(DEFAULT_START_Y);
        assertThat(testSensorCoverageRange.getEndX()).isEqualTo(DEFAULT_END_X);
        assertThat(testSensorCoverageRange.getEndY()).isEqualTo(DEFAULT_END_Y);
        assertThat(testSensorCoverageRange.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSensorCoverageRange.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSensorCoverageRange.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSensorCoverageRange.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSensorCoverageRange.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSensorCoverageRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensorCoverageRangeRepository.findAll().size();

        // Create the SensorCoverageRange with an existing ID
        sensorCoverageRange.setId(1L);
        SensorCoverageRangeDTO sensorCoverageRangeDTO = sensorCoverageRangeMapper.toDto(sensorCoverageRange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorCoverageRangeMockMvc.perform(post("/api/sensor-coverage-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorCoverageRangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SensorCoverageRange in the database
        List<SensorCoverageRange> sensorCoverageRangeList = sensorCoverageRangeRepository.findAll();
        assertThat(sensorCoverageRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSensorCoverageRanges() throws Exception {
        // Initialize the database
        sensorCoverageRangeRepository.saveAndFlush(sensorCoverageRange);

        // Get all the sensorCoverageRangeList
        restSensorCoverageRangeMockMvc.perform(get("/api/sensor-coverage-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorCoverageRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].startX").value(hasItem(DEFAULT_START_X.doubleValue())))
            .andExpect(jsonPath("$.[*].startY").value(hasItem(DEFAULT_START_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].endX").value(hasItem(DEFAULT_END_X.doubleValue())))
            .andExpect(jsonPath("$.[*].endY").value(hasItem(DEFAULT_END_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getSensorCoverageRange() throws Exception {
        // Initialize the database
        sensorCoverageRangeRepository.saveAndFlush(sensorCoverageRange);

        // Get the sensorCoverageRange
        restSensorCoverageRangeMockMvc.perform(get("/api/sensor-coverage-ranges/{id}", sensorCoverageRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensorCoverageRange.getId().intValue()))
            .andExpect(jsonPath("$.startX").value(DEFAULT_START_X.doubleValue()))
            .andExpect(jsonPath("$.startY").value(DEFAULT_START_Y.doubleValue()))
            .andExpect(jsonPath("$.endX").value(DEFAULT_END_X.doubleValue()))
            .andExpect(jsonPath("$.endY").value(DEFAULT_END_Y.doubleValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSensorCoverageRange() throws Exception {
        // Get the sensorCoverageRange
        restSensorCoverageRangeMockMvc.perform(get("/api/sensor-coverage-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSensorCoverageRange() throws Exception {
        // Initialize the database
        sensorCoverageRangeRepository.saveAndFlush(sensorCoverageRange);
        int databaseSizeBeforeUpdate = sensorCoverageRangeRepository.findAll().size();

        // Update the sensorCoverageRange
        SensorCoverageRange updatedSensorCoverageRange = sensorCoverageRangeRepository.findOne(sensorCoverageRange.getId());
        // Disconnect from session so that the updates on updatedSensorCoverageRange are not directly saved in db
        em.detach(updatedSensorCoverageRange);
        updatedSensorCoverageRange
            .startX(UPDATED_START_X)
            .startY(UPDATED_START_Y)
            .endX(UPDATED_END_X)
            .endY(UPDATED_END_Y)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        SensorCoverageRangeDTO sensorCoverageRangeDTO = sensorCoverageRangeMapper.toDto(updatedSensorCoverageRange);

        restSensorCoverageRangeMockMvc.perform(put("/api/sensor-coverage-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorCoverageRangeDTO)))
            .andExpect(status().isOk());

        // Validate the SensorCoverageRange in the database
        List<SensorCoverageRange> sensorCoverageRangeList = sensorCoverageRangeRepository.findAll();
        assertThat(sensorCoverageRangeList).hasSize(databaseSizeBeforeUpdate);
        SensorCoverageRange testSensorCoverageRange = sensorCoverageRangeList.get(sensorCoverageRangeList.size() - 1);
        assertThat(testSensorCoverageRange.getStartX()).isEqualTo(UPDATED_START_X);
        assertThat(testSensorCoverageRange.getStartY()).isEqualTo(UPDATED_START_Y);
        assertThat(testSensorCoverageRange.getEndX()).isEqualTo(UPDATED_END_X);
        assertThat(testSensorCoverageRange.getEndY()).isEqualTo(UPDATED_END_Y);
        assertThat(testSensorCoverageRange.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSensorCoverageRange.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSensorCoverageRange.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSensorCoverageRange.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSensorCoverageRange.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSensorCoverageRange() throws Exception {
        int databaseSizeBeforeUpdate = sensorCoverageRangeRepository.findAll().size();

        // Create the SensorCoverageRange
        SensorCoverageRangeDTO sensorCoverageRangeDTO = sensorCoverageRangeMapper.toDto(sensorCoverageRange);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSensorCoverageRangeMockMvc.perform(put("/api/sensor-coverage-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorCoverageRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the SensorCoverageRange in the database
        List<SensorCoverageRange> sensorCoverageRangeList = sensorCoverageRangeRepository.findAll();
        assertThat(sensorCoverageRangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSensorCoverageRange() throws Exception {
        // Initialize the database
        sensorCoverageRangeRepository.saveAndFlush(sensorCoverageRange);
        int databaseSizeBeforeDelete = sensorCoverageRangeRepository.findAll().size();

        // Get the sensorCoverageRange
        restSensorCoverageRangeMockMvc.perform(delete("/api/sensor-coverage-ranges/{id}", sensorCoverageRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SensorCoverageRange> sensorCoverageRangeList = sensorCoverageRangeRepository.findAll();
        assertThat(sensorCoverageRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorCoverageRange.class);
        SensorCoverageRange sensorCoverageRange1 = new SensorCoverageRange();
        sensorCoverageRange1.setId(1L);
        SensorCoverageRange sensorCoverageRange2 = new SensorCoverageRange();
        sensorCoverageRange2.setId(sensorCoverageRange1.getId());
        assertThat(sensorCoverageRange1).isEqualTo(sensorCoverageRange2);
        sensorCoverageRange2.setId(2L);
        assertThat(sensorCoverageRange1).isNotEqualTo(sensorCoverageRange2);
        sensorCoverageRange1.setId(null);
        assertThat(sensorCoverageRange1).isNotEqualTo(sensorCoverageRange2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorCoverageRangeDTO.class);
        SensorCoverageRangeDTO sensorCoverageRangeDTO1 = new SensorCoverageRangeDTO();
        sensorCoverageRangeDTO1.setId(1L);
        SensorCoverageRangeDTO sensorCoverageRangeDTO2 = new SensorCoverageRangeDTO();
        assertThat(sensorCoverageRangeDTO1).isNotEqualTo(sensorCoverageRangeDTO2);
        sensorCoverageRangeDTO2.setId(sensorCoverageRangeDTO1.getId());
        assertThat(sensorCoverageRangeDTO1).isEqualTo(sensorCoverageRangeDTO2);
        sensorCoverageRangeDTO2.setId(2L);
        assertThat(sensorCoverageRangeDTO1).isNotEqualTo(sensorCoverageRangeDTO2);
        sensorCoverageRangeDTO1.setId(null);
        assertThat(sensorCoverageRangeDTO1).isNotEqualTo(sensorCoverageRangeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sensorCoverageRangeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sensorCoverageRangeMapper.fromId(null)).isNull();
    }
}
*/