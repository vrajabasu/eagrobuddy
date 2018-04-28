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
import com.eagro.entities.Section;
import com.eagro.repository.SectionRepository;
import com.eagro.service.SectionService;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.SectionMapper;

*//**
 * Test class for the SectionResource REST controller.
 *
 * @see SectionResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class SectionResourceIntTest {

    private static final Long DEFAULT_SECTION_ID = 1L;
    private static final Long UPDATED_SECTION_ID = 2L;

    private static final String DEFAULT_SECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECTION_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SECTION_DESC = "BBBBBBBBBB";

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
    private SectionRepository sectionRepository;

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionMockMvc;

    private Section section;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionResource sectionResource = new SectionResource(sectionService);
        this.restSectionMockMvc = MockMvcBuilders.standaloneSetup(sectionResource)
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
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .sectionId(DEFAULT_SECTION_ID)
            .sectionName(DEFAULT_SECTION_NAME)
            .sectionDesc(DEFAULT_SECTION_DESC)
            .startX(DEFAULT_START_X)
            .startY(DEFAULT_START_Y)
            .endX(DEFAULT_END_X)
            .endY(DEFAULT_END_Y)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return section;
    }

    @Before
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    public void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionId()).isEqualTo(DEFAULT_SECTION_ID);
        assertThat(testSection.getSectionName()).isEqualTo(DEFAULT_SECTION_NAME);
        assertThat(testSection.getSectionDesc()).isEqualTo(DEFAULT_SECTION_DESC);
        assertThat(testSection.getStartX()).isEqualTo(DEFAULT_START_X);
        assertThat(testSection.getStartY()).isEqualTo(DEFAULT_START_Y);
        assertThat(testSection.getEndX()).isEqualTo(DEFAULT_END_X);
        assertThat(testSection.getEndY()).isEqualTo(DEFAULT_END_Y);
        assertThat(testSection.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSection.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSection.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSection.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section with an existing ID
        section.setId(1L);
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].sectionId").value(hasItem(DEFAULT_SECTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].sectionName").value(hasItem(DEFAULT_SECTION_NAME.toString())))
            .andExpect(jsonPath("$.[*].sectionDesc").value(hasItem(DEFAULT_SECTION_DESC.toString())))
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
    public void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.sectionId").value(DEFAULT_SECTION_ID.intValue()))
            .andExpect(jsonPath("$.sectionName").value(DEFAULT_SECTION_NAME.toString()))
            .andExpect(jsonPath("$.sectionDesc").value(DEFAULT_SECTION_DESC.toString()))
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
    public void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findOne(section.getId());
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .sectionId(UPDATED_SECTION_ID)
            .sectionName(UPDATED_SECTION_NAME)
            .sectionDesc(UPDATED_SECTION_DESC)
            .startX(UPDATED_START_X)
            .startY(UPDATED_START_Y)
            .endX(UPDATED_END_X)
            .endY(UPDATED_END_Y)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        SectionDTO sectionDTO = sectionMapper.toDto(updatedSection);

        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getSectionId()).isEqualTo(UPDATED_SECTION_ID);
        assertThat(testSection.getSectionName()).isEqualTo(UPDATED_SECTION_NAME);
        assertThat(testSection.getSectionDesc()).isEqualTo(UPDATED_SECTION_DESC);
        assertThat(testSection.getStartX()).isEqualTo(UPDATED_START_X);
        assertThat(testSection.getStartY()).isEqualTo(UPDATED_START_Y);
        assertThat(testSection.getEndX()).isEqualTo(UPDATED_END_X);
        assertThat(testSection.getEndY()).isEqualTo(UPDATED_END_Y);
        assertThat(testSection.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSection.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSection.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);
        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Get the section
        restSectionMockMvc.perform(delete("/api/sections/{id}", section.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Section.class);
        Section section1 = new Section();
        section1.setId(1L);
        Section section2 = new Section();
        section2.setId(section1.getId());
        assertThat(section1).isEqualTo(section2);
        section2.setId(2L);
        assertThat(section1).isNotEqualTo(section2);
        section1.setId(null);
        assertThat(section1).isNotEqualTo(section2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionDTO.class);
        SectionDTO sectionDTO1 = new SectionDTO();
        sectionDTO1.setId(1L);
        SectionDTO sectionDTO2 = new SectionDTO();
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO2.setId(sectionDTO1.getId());
        assertThat(sectionDTO1).isEqualTo(sectionDTO2);
        sectionDTO2.setId(2L);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO1.setId(null);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sectionMapper.fromId(null)).isNull();
    }
}
*/