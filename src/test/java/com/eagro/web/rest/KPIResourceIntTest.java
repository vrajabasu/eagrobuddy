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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.EagroserviceApplication;
import com.eagro.config.TestContext;
import com.eagro.entities.KPI;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.KPIRepository;
import com.eagro.service.KPIService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.KPIMapper;
*//**
 * Test class for the KPIResource REST controller.
 *
 * @see KPIResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
@ContextConfiguration(classes = { TestContext.class })
public class KPIResourceIntTest {

    private static final Long DEFAULT_KPI_ID = 1L;
    private static final Long UPDATED_KPI_ID = 2L;

    private static final String DEFAULT_KPI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_KPI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KPI_DESC = "AAAAAAAAAA";
    private static final String UPDATED_KPI_DESC = "BBBBBBBBBB";

    private static final ZoneType DEFAULT_ZONE_TYPE = ZoneType.WATER;
    private static final ZoneType UPDATED_ZONE_TYPE = ZoneType.ROOT;

    private static final Double DEFAULT_LOWER_REF_LIMIT = 1D;
    private static final Double UPDATED_LOWER_REF_LIMIT = 2D;

    private static final Double DEFAULT_UPPER_REF_LIMIT = 1D;
    private static final Double UPDATED_UPPER_REF_LIMIT = 2D;

    private static final Double DEFAULT_OPTIMAL_VALUE = 1D;
    private static final Double UPDATED_OPTIMAL_VALUE = 2D;

    private static final Double DEFAULT_DEVIATION_RANGE = 1D;
    private static final Double UPDATED_DEVIATION_RANGE = 2D;

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
    private KPIRepository kPIRepository;

    @Autowired
    private KPIMapper kPIMapper;

    @Autowired
    private KPIService kPIService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKPIMockMvc;

    private KPI kPI;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KPIResource kPIResource = new KPIResource(kPIService);
        this.restKPIMockMvc = MockMvcBuilders.standaloneSetup(kPIResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            
           // .setControllerAdvice(exceptionTranslator)
           // .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static KPI createEntity(EntityManager em) {
        KPI kPI = new KPI()
            .kpiId(DEFAULT_KPI_ID)
            .kpiName(DEFAULT_KPI_NAME)
            .kpiDesc(DEFAULT_KPI_DESC)
            .zoneType(DEFAULT_ZONE_TYPE)
            .lowerRefLimit(DEFAULT_LOWER_REF_LIMIT)
            .upperRefLimit(DEFAULT_UPPER_REF_LIMIT)
            .optimalValue(DEFAULT_OPTIMAL_VALUE)
            .deviationRange(DEFAULT_DEVIATION_RANGE)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return kPI;
    }

    @Before
    public void initTest() {
        kPI = createEntity(em);
    }

    @Test
    @Transactional
    public void createKPI() throws Exception {
        int databaseSizeBeforeCreate = kPIRepository.findAll().size();

        // Create the KPI
        KPIDTO kPIDTO = kPIMapper.toDto(kPI);
        restKPIMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kPIDTO)))
            .andExpect(status().isCreated());

        // Validate the KPI in the database
        List<KPI> kPIList = kPIRepository.findAll();
        assertThat(kPIList).hasSize(databaseSizeBeforeCreate + 1);
        KPI testKPI = kPIList.get(kPIList.size() - 1);
        assertThat(testKPI.getKpiId()).isEqualTo(DEFAULT_KPI_ID);
        assertThat(testKPI.getKpiName()).isEqualTo(DEFAULT_KPI_NAME);
        assertThat(testKPI.getKpiDesc()).isEqualTo(DEFAULT_KPI_DESC);
        assertThat(testKPI.getZoneType()).isEqualTo(DEFAULT_ZONE_TYPE);
        assertThat(testKPI.getLowerRefLimit()).isEqualTo(DEFAULT_LOWER_REF_LIMIT);
        assertThat(testKPI.getUpperRefLimit()).isEqualTo(DEFAULT_UPPER_REF_LIMIT);
        assertThat(testKPI.getOptimalValue()).isEqualTo(DEFAULT_OPTIMAL_VALUE);
        assertThat(testKPI.getDeviationRange()).isEqualTo(DEFAULT_DEVIATION_RANGE);
        assertThat(testKPI.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testKPI.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testKPI.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testKPI.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testKPI.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createKPIWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kPIRepository.findAll().size();

        // Create the KPI with an existing ID
        kPI.setId(1L);
        KPIDTO kPIDTO = kPIMapper.toDto(kPI);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKPIMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kPIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the KPI in the database
        List<KPI> kPIList = kPIRepository.findAll();
        assertThat(kPIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKPIS() throws Exception {
        // Initialize the database
        kPIRepository.saveAndFlush(kPI);

        // Get all the kPIList
        restKPIMockMvc.perform(get("/api/kpis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kPI.getId().intValue())))
            .andExpect(jsonPath("$.[*].kpiId").value(hasItem(DEFAULT_KPI_ID.intValue())))
            .andExpect(jsonPath("$.[*].kpiName").value(hasItem(DEFAULT_KPI_NAME.toString())))
            .andExpect(jsonPath("$.[*].kpiDesc").value(hasItem(DEFAULT_KPI_DESC.toString())))
            .andExpect(jsonPath("$.[*].zoneType").value(hasItem(DEFAULT_ZONE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lowerRefLimit").value(hasItem(DEFAULT_LOWER_REF_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].upperRefLimit").value(hasItem(DEFAULT_UPPER_REF_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].optimalValue").value(hasItem(DEFAULT_OPTIMAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].deviationRange").value(hasItem(DEFAULT_DEVIATION_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getKPI() throws Exception {
        // Initialize the database
        kPIRepository.saveAndFlush(kPI);

        // Get the kPI
        restKPIMockMvc.perform(get("/api/kpis/{id}", kPI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kPI.getId().intValue()))
            .andExpect(jsonPath("$.kpiId").value(DEFAULT_KPI_ID.intValue()))
            .andExpect(jsonPath("$.kpiName").value(DEFAULT_KPI_NAME.toString()))
            .andExpect(jsonPath("$.kpiDesc").value(DEFAULT_KPI_DESC.toString()))
            .andExpect(jsonPath("$.zoneType").value(DEFAULT_ZONE_TYPE.toString()))
            .andExpect(jsonPath("$.lowerRefLimit").value(DEFAULT_LOWER_REF_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.upperRefLimit").value(DEFAULT_UPPER_REF_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.optimalValue").value(DEFAULT_OPTIMAL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.deviationRange").value(DEFAULT_DEVIATION_RANGE.doubleValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKPI() throws Exception {
        // Get the kPI
        restKPIMockMvc.perform(get("/api/kpis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKPI() throws Exception {
        // Initialize the database
        kPIRepository.saveAndFlush(kPI);
        int databaseSizeBeforeUpdate = kPIRepository.findAll().size();

        // Update the kPI
        KPI updatedKPI = kPIRepository.findOne(kPI.getId());
        // Disconnect from session so that the updates on updatedKPI are not directly saved in db
        em.detach(updatedKPI);
        updatedKPI
            .kpiId(UPDATED_KPI_ID)
            .kpiName(UPDATED_KPI_NAME)
            .kpiDesc(UPDATED_KPI_DESC)
            .zoneType(UPDATED_ZONE_TYPE)
            .lowerRefLimit(UPDATED_LOWER_REF_LIMIT)
            .upperRefLimit(UPDATED_UPPER_REF_LIMIT)
            .optimalValue(UPDATED_OPTIMAL_VALUE)
            .deviationRange(UPDATED_DEVIATION_RANGE)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        KPIDTO kPIDTO = kPIMapper.toDto(updatedKPI);

        restKPIMockMvc.perform(put("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kPIDTO)))
            .andExpect(status().isOk());

        // Validate the KPI in the database
        List<KPI> kPIList = kPIRepository.findAll();
        assertThat(kPIList).hasSize(databaseSizeBeforeUpdate);
        KPI testKPI = kPIList.get(kPIList.size() - 1);
        assertThat(testKPI.getKpiId()).isEqualTo(UPDATED_KPI_ID);
        assertThat(testKPI.getKpiName()).isEqualTo(UPDATED_KPI_NAME);
        assertThat(testKPI.getKpiDesc()).isEqualTo(UPDATED_KPI_DESC);
        assertThat(testKPI.getZoneType()).isEqualTo(UPDATED_ZONE_TYPE);
        assertThat(testKPI.getLowerRefLimit()).isEqualTo(UPDATED_LOWER_REF_LIMIT);
        assertThat(testKPI.getUpperRefLimit()).isEqualTo(UPDATED_UPPER_REF_LIMIT);
        assertThat(testKPI.getOptimalValue()).isEqualTo(UPDATED_OPTIMAL_VALUE);
        assertThat(testKPI.getDeviationRange()).isEqualTo(UPDATED_DEVIATION_RANGE);
        assertThat(testKPI.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testKPI.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testKPI.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testKPI.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testKPI.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingKPI() throws Exception {
        int databaseSizeBeforeUpdate = kPIRepository.findAll().size();

        // Create the KPI
        KPIDTO kPIDTO = kPIMapper.toDto(kPI);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKPIMockMvc.perform(put("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kPIDTO)))
            .andExpect(status().isCreated());

        // Validate the KPI in the database
        List<KPI> kPIList = kPIRepository.findAll();
        assertThat(kPIList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKPI() throws Exception {
        // Initialize the database
        kPIRepository.saveAndFlush(kPI);
        int databaseSizeBeforeDelete = kPIRepository.findAll().size();

        // Get the kPI
        restKPIMockMvc.perform(delete("/api/kpis/{id}", kPI.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KPI> kPIList = kPIRepository.findAll();
        assertThat(kPIList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KPI.class);
        KPI kPI1 = new KPI();
        kPI1.setId(1L);
        KPI kPI2 = new KPI();
        kPI2.setId(kPI1.getId());
        assertThat(kPI1).isEqualTo(kPI2);
        kPI2.setId(2L);
        assertThat(kPI1).isNotEqualTo(kPI2);
        kPI1.setId(null);
        assertThat(kPI1).isNotEqualTo(kPI2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KPIDTO.class);
        KPIDTO kPIDTO1 = new KPIDTO();
        kPIDTO1.setId(1L);
        KPIDTO kPIDTO2 = new KPIDTO();
        assertThat(kPIDTO1).isNotEqualTo(kPIDTO2);
        kPIDTO2.setId(kPIDTO1.getId());
        assertThat(kPIDTO1).isEqualTo(kPIDTO2);
        kPIDTO2.setId(2L);
        assertThat(kPIDTO1).isNotEqualTo(kPIDTO2);
        kPIDTO1.setId(null);
        assertThat(kPIDTO1).isNotEqualTo(kPIDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kPIMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kPIMapper.fromId(null)).isNull();
    }
}
*/