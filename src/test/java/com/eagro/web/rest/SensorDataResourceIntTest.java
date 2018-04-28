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
import com.eagro.entities.SensorData;
import com.eagro.repository.SensorDataRepository;
import com.eagro.service.SensorDataService;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.SensorDataMapper;

*//**
 * Test class for the SensorDataResource REST controller.
 *
 * @see SensorDataResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class SensorDataResourceIntTest {

    private static final LocalDate DEFAULT_RECORDED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECORDED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PARAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_1 = "BBBBBBBBBB";

    private static final Double DEFAULT_PARAM_VALUE_1 = 1D;
    private static final Double UPDATED_PARAM_VALUE_1 = 2D;

    private static final String DEFAULT_PARAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_2 = "BBBBBBBBBB";

    private static final Double DEFAULT_PARAM_VALUE_2 = 1D;
    private static final Double UPDATED_PARAM_VALUE_2 = 2D;

    private static final String DEFAULT_PARAM_3 = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_3 = "BBBBBBBBBB";

    private static final Double DEFAULT_PARAM_VALUE_3 = 1D;
    private static final Double UPDATED_PARAM_VALUE_3 = 2D;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorDataMapper sensorDataMapper;

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSensorDataMockMvc;

    private SensorData sensorData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SensorDataResource sensorDataResource = new SensorDataResource(sensorDataService);
        this.restSensorDataMockMvc = MockMvcBuilders.standaloneSetup(sensorDataResource)
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
    public static SensorData createEntity(EntityManager em) {
        SensorData sensorData = new SensorData()
            .recordedDateTime(DEFAULT_RECORDED_DATE_TIME)
            .param1(DEFAULT_PARAM_1)
            .paramValue1(DEFAULT_PARAM_VALUE_1)
            .param2(DEFAULT_PARAM_2)
            .paramValue2(DEFAULT_PARAM_VALUE_2)
            .param3(DEFAULT_PARAM_3)
            .paramValue3(DEFAULT_PARAM_VALUE_3);
        return sensorData;
    }

    @Before
    public void initTest() {
        sensorData = createEntity(em);
    }

    @Test
    @Transactional
    public void createSensorData() throws Exception {
        int databaseSizeBeforeCreate = sensorDataRepository.findAll().size();

        // Create the SensorData
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);
        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isCreated());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeCreate + 1);
        SensorData testSensorData = sensorDataList.get(sensorDataList.size() - 1);
        assertThat(testSensorData.getRecordedDateTime()).isEqualTo(DEFAULT_RECORDED_DATE_TIME);
        assertThat(testSensorData.getParam1()).isEqualTo(DEFAULT_PARAM_1);
        assertThat(testSensorData.getParamValue1()).isEqualTo(DEFAULT_PARAM_VALUE_1);
        assertThat(testSensorData.getParam2()).isEqualTo(DEFAULT_PARAM_2);
        assertThat(testSensorData.getParamValue2()).isEqualTo(DEFAULT_PARAM_VALUE_2);
        assertThat(testSensorData.getParam3()).isEqualTo(DEFAULT_PARAM_3);
        assertThat(testSensorData.getParamValue3()).isEqualTo(DEFAULT_PARAM_VALUE_3);
    }

    @Test
    @Transactional
    public void createSensorDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensorDataRepository.findAll().size();

        // Create the SensorData with an existing ID
        sensorData.setId(1L);
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorDataMockMvc.perform(post("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.saveAndFlush(sensorData);

        // Get all the sensorDataList
        restSensorDataMockMvc.perform(get("/api/sensor-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensorData.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordedDateTime").value(hasItem(DEFAULT_RECORDED_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].param1").value(hasItem(DEFAULT_PARAM_1.toString())))
            .andExpect(jsonPath("$.[*].paramValue1").value(hasItem(DEFAULT_PARAM_VALUE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].param2").value(hasItem(DEFAULT_PARAM_2.toString())))
            .andExpect(jsonPath("$.[*].paramValue2").value(hasItem(DEFAULT_PARAM_VALUE_2.doubleValue())))
            .andExpect(jsonPath("$.[*].param3").value(hasItem(DEFAULT_PARAM_3.toString())))
            .andExpect(jsonPath("$.[*].paramValue3").value(hasItem(DEFAULT_PARAM_VALUE_3.doubleValue())));
    }

    @Test
    @Transactional
    public void getSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.saveAndFlush(sensorData);

        // Get the sensorData
        restSensorDataMockMvc.perform(get("/api/sensor-data/{id}", sensorData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensorData.getId().intValue()))
            .andExpect(jsonPath("$.recordedDateTime").value(DEFAULT_RECORDED_DATE_TIME.toString()))
            .andExpect(jsonPath("$.param1").value(DEFAULT_PARAM_1.toString()))
            .andExpect(jsonPath("$.paramValue1").value(DEFAULT_PARAM_VALUE_1.doubleValue()))
            .andExpect(jsonPath("$.param2").value(DEFAULT_PARAM_2.toString()))
            .andExpect(jsonPath("$.paramValue2").value(DEFAULT_PARAM_VALUE_2.doubleValue()))
            .andExpect(jsonPath("$.param3").value(DEFAULT_PARAM_3.toString()))
            .andExpect(jsonPath("$.paramValue3").value(DEFAULT_PARAM_VALUE_3.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSensorData() throws Exception {
        // Get the sensorData
        restSensorDataMockMvc.perform(get("/api/sensor-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.saveAndFlush(sensorData);
        int databaseSizeBeforeUpdate = sensorDataRepository.findAll().size();

        // Update the sensorData
        SensorData updatedSensorData = sensorDataRepository.findOne(sensorData.getId());
        // Disconnect from session so that the updates on updatedSensorData are not directly saved in db
        em.detach(updatedSensorData);
        updatedSensorData
            .recordedDateTime(UPDATED_RECORDED_DATE_TIME)
            .param1(UPDATED_PARAM_1)
            .paramValue1(UPDATED_PARAM_VALUE_1)
            .param2(UPDATED_PARAM_2)
            .paramValue2(UPDATED_PARAM_VALUE_2)
            .param3(UPDATED_PARAM_3)
            .paramValue3(UPDATED_PARAM_VALUE_3);
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(updatedSensorData);

        restSensorDataMockMvc.perform(put("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isOk());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeUpdate);
        SensorData testSensorData = sensorDataList.get(sensorDataList.size() - 1);
        assertThat(testSensorData.getRecordedDateTime()).isEqualTo(UPDATED_RECORDED_DATE_TIME);
        assertThat(testSensorData.getParam1()).isEqualTo(UPDATED_PARAM_1);
        assertThat(testSensorData.getParamValue1()).isEqualTo(UPDATED_PARAM_VALUE_1);
        assertThat(testSensorData.getParam2()).isEqualTo(UPDATED_PARAM_2);
        assertThat(testSensorData.getParamValue2()).isEqualTo(UPDATED_PARAM_VALUE_2);
        assertThat(testSensorData.getParam3()).isEqualTo(UPDATED_PARAM_3);
        assertThat(testSensorData.getParamValue3()).isEqualTo(UPDATED_PARAM_VALUE_3);
    }

    @Test
    @Transactional
    public void updateNonExistingSensorData() throws Exception {
        int databaseSizeBeforeUpdate = sensorDataRepository.findAll().size();

        // Create the SensorData
        SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSensorDataMockMvc.perform(put("/api/sensor-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensorDataDTO)))
            .andExpect(status().isCreated());

        // Validate the SensorData in the database
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSensorData() throws Exception {
        // Initialize the database
        sensorDataRepository.saveAndFlush(sensorData);
        int databaseSizeBeforeDelete = sensorDataRepository.findAll().size();

        // Get the sensorData
        restSensorDataMockMvc.perform(delete("/api/sensor-data/{id}", sensorData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        assertThat(sensorDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorData.class);
        SensorData sensorData1 = new SensorData();
        sensorData1.setId(1L);
        SensorData sensorData2 = new SensorData();
        sensorData2.setId(sensorData1.getId());
        assertThat(sensorData1).isEqualTo(sensorData2);
        sensorData2.setId(2L);
        assertThat(sensorData1).isNotEqualTo(sensorData2);
        sensorData1.setId(null);
        assertThat(sensorData1).isNotEqualTo(sensorData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SensorDataDTO.class);
        SensorDataDTO sensorDataDTO1 = new SensorDataDTO();
        sensorDataDTO1.setId(1L);
        SensorDataDTO sensorDataDTO2 = new SensorDataDTO();
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
        sensorDataDTO2.setId(sensorDataDTO1.getId());
        assertThat(sensorDataDTO1).isEqualTo(sensorDataDTO2);
        sensorDataDTO2.setId(2L);
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
        sensorDataDTO1.setId(null);
        assertThat(sensorDataDTO1).isNotEqualTo(sensorDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sensorDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sensorDataMapper.fromId(null)).isNull();
    }
}
*/