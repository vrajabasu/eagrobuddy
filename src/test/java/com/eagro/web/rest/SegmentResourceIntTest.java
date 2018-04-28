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
import com.eagro.entities.Segment;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.SegmentService;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.SegmentMapper;

*//**
 * Test class for the SegmentResource REST controller.
 *
 * @see SegmentResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class SegmentResourceIntTest {

    private static final Long DEFAULT_SEGMENT_ID = 1L;
    private static final Long UPDATED_SEGMENT_ID = 2L;

    private static final String DEFAULT_SEGMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEGMENT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SEGMENT_DESC = "BBBBBBBBBB";

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
    private SegmentRepository segmentRepository;

    @Autowired
    private SegmentMapper segmentMapper;

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSegmentMockMvc;

    private Segment segment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SegmentResource segmentResource = new SegmentResource(segmentService);
        this.restSegmentMockMvc = MockMvcBuilders.standaloneSetup(segmentResource)
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
    public static Segment createEntity(EntityManager em) {
        Segment segment = new Segment()
            .segmentId(DEFAULT_SEGMENT_ID)
            .segmentName(DEFAULT_SEGMENT_NAME)
            .segmentDesc(DEFAULT_SEGMENT_DESC)
            .startX(DEFAULT_START_X)
            .startY(DEFAULT_START_Y)
            .endX(DEFAULT_END_X)
            .endY(DEFAULT_END_Y)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return segment;
    }

    @Before
    public void initTest() {
        segment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSegment() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);
        restSegmentMockMvc.perform(post("/api/segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate + 1);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getSegmentId()).isEqualTo(DEFAULT_SEGMENT_ID);
        assertThat(testSegment.getSegmentName()).isEqualTo(DEFAULT_SEGMENT_NAME);
        assertThat(testSegment.getSegmentDesc()).isEqualTo(DEFAULT_SEGMENT_DESC);
        assertThat(testSegment.getStartX()).isEqualTo(DEFAULT_START_X);
        assertThat(testSegment.getStartY()).isEqualTo(DEFAULT_START_Y);
        assertThat(testSegment.getEndX()).isEqualTo(DEFAULT_END_X);
        assertThat(testSegment.getEndY()).isEqualTo(DEFAULT_END_Y);
        assertThat(testSegment.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSegment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSegment.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSegmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();

        // Create the Segment with an existing ID
        segment.setId(1L);
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentMockMvc.perform(post("/api/segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSegments() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get all the segmentList
        restSegmentMockMvc.perform(get("/api/segments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segment.getId().intValue())))
            .andExpect(jsonPath("$.[*].segmentId").value(hasItem(DEFAULT_SEGMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].segmentName").value(hasItem(DEFAULT_SEGMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].segmentDesc").value(hasItem(DEFAULT_SEGMENT_DESC.toString())))
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
    public void getSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get the segment
        restSegmentMockMvc.perform(get("/api/segments/{id}", segment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segment.getId().intValue()))
            .andExpect(jsonPath("$.segmentId").value(DEFAULT_SEGMENT_ID.intValue()))
            .andExpect(jsonPath("$.segmentName").value(DEFAULT_SEGMENT_NAME.toString()))
            .andExpect(jsonPath("$.segmentDesc").value(DEFAULT_SEGMENT_DESC.toString()))
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
    public void getNonExistingSegment() throws Exception {
        // Get the segment
        restSegmentMockMvc.perform(get("/api/segments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment
        Segment updatedSegment = segmentRepository.findOne(segment.getId());
        // Disconnect from session so that the updates on updatedSegment are not directly saved in db
        em.detach(updatedSegment);
        updatedSegment
            .segmentId(UPDATED_SEGMENT_ID)
            .segmentName(UPDATED_SEGMENT_NAME)
            .segmentDesc(UPDATED_SEGMENT_DESC)
            .startX(UPDATED_START_X)
            .startY(UPDATED_START_Y)
            .endX(UPDATED_END_X)
            .endY(UPDATED_END_Y)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        SegmentDTO segmentDTO = segmentMapper.toDto(updatedSegment);

        restSegmentMockMvc.perform(put("/api/segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getSegmentId()).isEqualTo(UPDATED_SEGMENT_ID);
        assertThat(testSegment.getSegmentName()).isEqualTo(UPDATED_SEGMENT_NAME);
        assertThat(testSegment.getSegmentDesc()).isEqualTo(UPDATED_SEGMENT_DESC);
        assertThat(testSegment.getStartX()).isEqualTo(UPDATED_START_X);
        assertThat(testSegment.getStartY()).isEqualTo(UPDATED_START_Y);
        assertThat(testSegment.getEndX()).isEqualTo(UPDATED_END_X);
        assertThat(testSegment.getEndY()).isEqualTo(UPDATED_END_Y);
        assertThat(testSegment.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSegment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSegment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSegment.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testSegment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Create the Segment
        SegmentDTO segmentDTO = segmentMapper.toDto(segment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSegmentMockMvc.perform(put("/api/segments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(segmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);
        int databaseSizeBeforeDelete = segmentRepository.findAll().size();

        // Get the segment
        restSegmentMockMvc.perform(delete("/api/segments/{id}", segment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Segment.class);
        Segment segment1 = new Segment();
        segment1.setId(1L);
        Segment segment2 = new Segment();
        segment2.setId(segment1.getId());
        assertThat(segment1).isEqualTo(segment2);
        segment2.setId(2L);
        assertThat(segment1).isNotEqualTo(segment2);
        segment1.setId(null);
        assertThat(segment1).isNotEqualTo(segment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentDTO.class);
        SegmentDTO segmentDTO1 = new SegmentDTO();
        segmentDTO1.setId(1L);
        SegmentDTO segmentDTO2 = new SegmentDTO();
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
        segmentDTO2.setId(segmentDTO1.getId());
        assertThat(segmentDTO1).isEqualTo(segmentDTO2);
        segmentDTO2.setId(2L);
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
        segmentDTO1.setId(null);
        assertThat(segmentDTO1).isNotEqualTo(segmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(segmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(segmentMapper.fromId(null)).isNull();
    }
}
*/