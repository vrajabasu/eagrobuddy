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
import com.eagro.entities.Layout;
import com.eagro.repository.LayoutRepository;
import com.eagro.service.LayoutService;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.exception.ExceptionTranslator;
import com.eagro.service.mapper.LayoutMapper;

*//**
 * Test class for the LayoutResource REST controller.
 *
 * @see LayoutResource
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EagroserviceApplication.class)
public class LayoutResourceIntTest {

    private static final Long DEFAULT_LAYOUT_ID = 1L;
    private static final Long UPDATED_LAYOUT_ID = 2L;

    private static final String DEFAULT_LAYOUT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAYOUT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAYOUT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_LAYOUT_DESC = "BBBBBBBBBB";

    private static final Double DEFAULT_WIDTH_X = 1D;
    private static final Double UPDATED_WIDTH_X = 2D;

    private static final Double DEFAULT_HEIGHT_Y = 1D;
    private static final Double UPDATED_HEIGHT_Y = 2D;

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
    private LayoutRepository layoutRepository;

    @Autowired
    private LayoutMapper layoutMapper;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLayoutMockMvc;

    private Layout layout;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LayoutResource layoutResource = new LayoutResource(layoutService);
        this.restLayoutMockMvc = MockMvcBuilders.standaloneSetup(layoutResource)
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
    public static Layout createEntity(EntityManager em) {
        Layout layout = new Layout()
            .layoutId(DEFAULT_LAYOUT_ID)
            .layoutName(DEFAULT_LAYOUT_NAME)
            .layoutDesc(DEFAULT_LAYOUT_DESC)
            .widthX(DEFAULT_WIDTH_X)
            .heightY(DEFAULT_HEIGHT_Y)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return layout;
    }

    @Before
    public void initTest() {
        layout = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayout() throws Exception {
        int databaseSizeBeforeCreate = layoutRepository.findAll().size();

        // Create the Layout
        LayoutDTO layoutDTO = layoutMapper.toDto(layout);
        restLayoutMockMvc.perform(post("/api/layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Layout in the database
        List<Layout> layoutList = layoutRepository.findAll();
        assertThat(layoutList).hasSize(databaseSizeBeforeCreate + 1);
        Layout testLayout = layoutList.get(layoutList.size() - 1);
        assertThat(testLayout.getLayoutId()).isEqualTo(DEFAULT_LAYOUT_ID);
        assertThat(testLayout.getLayoutName()).isEqualTo(DEFAULT_LAYOUT_NAME);
        assertThat(testLayout.getLayoutDesc()).isEqualTo(DEFAULT_LAYOUT_DESC);
        assertThat(testLayout.getWidthX()).isEqualTo(DEFAULT_WIDTH_X);
        assertThat(testLayout.getHeightY()).isEqualTo(DEFAULT_HEIGHT_Y);
        assertThat(testLayout.getActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testLayout.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLayout.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLayout.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testLayout.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createLayoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layoutRepository.findAll().size();

        // Create the Layout with an existing ID
        layout.setId(1L);
        LayoutDTO layoutDTO = layoutMapper.toDto(layout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayoutMockMvc.perform(post("/api/layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Layout in the database
        List<Layout> layoutList = layoutRepository.findAll();
        assertThat(layoutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLayouts() throws Exception {
        // Initialize the database
        layoutRepository.saveAndFlush(layout);

        // Get all the layoutList
        restLayoutMockMvc.perform(get("/api/layouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layout.getId().intValue())))
            .andExpect(jsonPath("$.[*].layoutId").value(hasItem(DEFAULT_LAYOUT_ID.intValue())))
            .andExpect(jsonPath("$.[*].layoutName").value(hasItem(DEFAULT_LAYOUT_NAME.toString())))
            .andExpect(jsonPath("$.[*].layoutDesc").value(hasItem(DEFAULT_LAYOUT_DESC.toString())))
            .andExpect(jsonPath("$.[*].widthX").value(hasItem(DEFAULT_WIDTH_X.doubleValue())))
            .andExpect(jsonPath("$.[*].heightY").value(hasItem(DEFAULT_HEIGHT_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getLayout() throws Exception {
        // Initialize the database
        layoutRepository.saveAndFlush(layout);

        // Get the layout
        restLayoutMockMvc.perform(get("/api/layouts/{id}", layout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(layout.getId().intValue()))
            .andExpect(jsonPath("$.layoutId").value(DEFAULT_LAYOUT_ID.intValue()))
            .andExpect(jsonPath("$.layoutName").value(DEFAULT_LAYOUT_NAME.toString()))
            .andExpect(jsonPath("$.layoutDesc").value(DEFAULT_LAYOUT_DESC.toString()))
            .andExpect(jsonPath("$.widthX").value(DEFAULT_WIDTH_X.doubleValue()))
            .andExpect(jsonPath("$.heightY").value(DEFAULT_HEIGHT_Y.doubleValue()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLayout() throws Exception {
        // Get the layout
        restLayoutMockMvc.perform(get("/api/layouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayout() throws Exception {
        // Initialize the database
        layoutRepository.saveAndFlush(layout);
        int databaseSizeBeforeUpdate = layoutRepository.findAll().size();

        // Update the layout
        Layout updatedLayout = layoutRepository.findOne(layout.getId());
        // Disconnect from session so that the updates on updatedLayout are not directly saved in db
        em.detach(updatedLayout);
        updatedLayout
            .layoutId(UPDATED_LAYOUT_ID)
            .layoutName(UPDATED_LAYOUT_NAME)
            .layoutDesc(UPDATED_LAYOUT_DESC)
            .widthX(UPDATED_WIDTH_X)
            .heightY(UPDATED_HEIGHT_Y)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        LayoutDTO layoutDTO = layoutMapper.toDto(updatedLayout);

        restLayoutMockMvc.perform(put("/api/layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layoutDTO)))
            .andExpect(status().isOk());

        // Validate the Layout in the database
        List<Layout> layoutList = layoutRepository.findAll();
        assertThat(layoutList).hasSize(databaseSizeBeforeUpdate);
        Layout testLayout = layoutList.get(layoutList.size() - 1);
        assertThat(testLayout.getLayoutId()).isEqualTo(UPDATED_LAYOUT_ID);
        assertThat(testLayout.getLayoutName()).isEqualTo(UPDATED_LAYOUT_NAME);
        assertThat(testLayout.getLayoutDesc()).isEqualTo(UPDATED_LAYOUT_DESC);
        assertThat(testLayout.getWidthX()).isEqualTo(UPDATED_WIDTH_X);
        assertThat(testLayout.getHeightY()).isEqualTo(UPDATED_HEIGHT_Y);
        assertThat(testLayout.getActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testLayout.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLayout.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLayout.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testLayout.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingLayout() throws Exception {
        int databaseSizeBeforeUpdate = layoutRepository.findAll().size();

        // Create the Layout
        LayoutDTO layoutDTO = layoutMapper.toDto(layout);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLayoutMockMvc.perform(put("/api/layouts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Layout in the database
        List<Layout> layoutList = layoutRepository.findAll();
        assertThat(layoutList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLayout() throws Exception {
        // Initialize the database
        layoutRepository.saveAndFlush(layout);
        int databaseSizeBeforeDelete = layoutRepository.findAll().size();

        // Get the layout
        restLayoutMockMvc.perform(delete("/api/layouts/{id}", layout.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Layout> layoutList = layoutRepository.findAll();
        assertThat(layoutList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Layout.class);
        Layout layout1 = new Layout();
        layout1.setId(1L);
        Layout layout2 = new Layout();
        layout2.setId(layout1.getId());
        assertThat(layout1).isEqualTo(layout2);
        layout2.setId(2L);
        assertThat(layout1).isNotEqualTo(layout2);
        layout1.setId(null);
        assertThat(layout1).isNotEqualTo(layout2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LayoutDTO.class);
        LayoutDTO layoutDTO1 = new LayoutDTO();
        layoutDTO1.setId(1L);
        LayoutDTO layoutDTO2 = new LayoutDTO();
        assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
        layoutDTO2.setId(layoutDTO1.getId());
        assertThat(layoutDTO1).isEqualTo(layoutDTO2);
        layoutDTO2.setId(2L);
        assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
        layoutDTO1.setId(null);
        assertThat(layoutDTO1).isNotEqualTo(layoutDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(layoutMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(layoutMapper.fromId(null)).isNull();
    }
}
*/