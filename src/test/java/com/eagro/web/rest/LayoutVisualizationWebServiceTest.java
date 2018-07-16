package com.eagro.web.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.eagro.config.TestContext;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentZoneDetailsResponse;
import com.eagro.service.dto.Segmentkpichart;
import com.eagro.service.dto.SegmentsResponseDTO;
import com.eagro.service.dto.SensorsResponse;
import com.eagro.service.dto.Zones;
import com.eagro.service.exception.ExceptionTranslator;
import com.google.common.collect.ImmutableList;

import io.swagger.model.KpiValuesForTheCurrentSegment;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
@WebAppConfiguration
public class LayoutVisualizationWebServiceTest {

	private MockMvc mockMvc;

	@Mock
	private ExceptionTranslator exceptionTranslator;

	@InjectMocks
	private LayoutVisualizationWebService layoutVisualizationWebService;

	@Mock
	private LayoutVisualizationService layoutVisualizationService;

	@Mock
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	private PodamFactory podam;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		 podam = new PodamFactoryImpl();
		ReflectionTestUtils.setField(layoutVisualizationWebService, "layoutVisualizationService",
				layoutVisualizationService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(layoutVisualizationWebService)
				.setControllerAdvice(exceptionTranslator).build();
	}

	@Test
	public void testGetLayoutOK() throws Exception {
		final SectionsResponseDTO sectionsResponseDTO = podam.manufacturePojoWithFullData(SectionsResponseDTO.class);
		final SegmentsResponseDTO segmentsResponseDTO = podam.manufacturePojoWithFullData(SegmentsResponseDTO.class);
		sectionsResponseDTO.addSegmentsItem(segmentsResponseDTO);
		final LayoutResponseDTO layoutResponseDTO = podam.manufacturePojoWithFullData(LayoutResponseDTO.class);
		layoutResponseDTO.addSectionsItem(sectionsResponseDTO);
		when(layoutVisualizationService.getLayoutDetails(any())).thenReturn(layoutResponseDTO);

		mockMvc.perform(
				get("/api/eAgro/v1/visualization/overall/layouts/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.layoutId", is(layoutResponseDTO.getLayoutId())))
				.andExpect(jsonPath("$.layoutName", is(layoutResponseDTO.getLayoutName())))
				.andExpect(jsonPath("$.layoutDescription", is(layoutResponseDTO.getLayoutDesc())))
				.andExpect(jsonPath("$.activeFlag", is(layoutResponseDTO.getActiveFlag())))
				.andExpect(jsonPath("$.createdBy", is(layoutResponseDTO.getCreatedBy())))
				.andExpect(jsonPath("$.sections[0].sectionName",
						is(layoutResponseDTO.getSections().get(0).getSectionName())))
				.andExpect(jsonPath("$.sections[0].sectionDescription",
						is(layoutResponseDTO.getSections().get(0).getSectionDescription())))
				.andExpect(
						jsonPath("$.sections[0].sectionId", is(layoutResponseDTO.getSections().get(0).getSectionId())))
				.andExpect(jsonPath("$.sections[0].segments.segmentName",
						is(layoutResponseDTO.getSections().get(0).getSegments().get(0).getSegmentName())))
				.andExpect(jsonPath("$.sections[0].segments[0].segmentId",
						is(layoutResponseDTO.getSections().get(0).getSegments().get(0).getSegmentId())));

	}
	
	@Test
	public void testGetLayoutNOTFoundException() throws Exception {
		mockMvc.perform(get("/api/eAgro/v1/visualization/layouts/1")
				 .contentType(MediaType.APPLICATION_JSON_UTF8)) 
				.andDo(print()).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetSectionOK() throws Exception {

		final SectionsResponseDTO sectionsResponseDTO = podam.manufacturePojoWithFullData(SectionsResponseDTO.class);
		SegmentsResponseDTO segmentsResponseDTO = podam.manufacturePojoWithFullData(SegmentsResponseDTO.class);
		sectionsResponseDTO.addSegmentsItem(segmentsResponseDTO);

		when(layoutVisualizationService.getSectionDetails(any(), any())).thenReturn(sectionsResponseDTO);

		mockMvc.perform(
				get("/api/eAgro/v1/visualization/overall/1/sections/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.sectionName", is(sectionsResponseDTO.getSectionName())))
				.andExpect(jsonPath("$.sectionDescription", is(sectionsResponseDTO.getSectionDescription())))
				.andExpect(jsonPath("$.sectionId", is(sectionsResponseDTO.getSectionId())))
				.andExpect(jsonPath("$.segments[0].segmentName",
						is(sectionsResponseDTO.getSegments().get(0).getSegmentName())))
				.andExpect(jsonPath("$.segments[0].segmentId",
						is(sectionsResponseDTO.getSegments().get(0).getSegmentId())));

	}
	
	@Test
	public void testGetOptimalKpiOK() throws Exception {

		final SectionwithkpiResponseDTO sectionwithkpiResponseDTO = podam
				.manufacturePojo(SectionwithkpiResponseDTO.class);
		when(layoutVisualizationService.getSectionBasedOptimalKpi(any(), any())).thenReturn(sectionwithkpiResponseDTO);

		mockMvc.perform(get("/api/eAgro/v1/visualization/optimalkpichart/1/sections/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.sectionName", is(sectionwithkpiResponseDTO.getSectionName())))
				.andExpect(jsonPath("$.sectionDescription", is(sectionwithkpiResponseDTO.getSectionDescription())))
				.andExpect(jsonPath("$.zoneWithKpis[0].optimalKpiValues[0].kpiName", is(
						sectionwithkpiResponseDTO.getZoneWithKpis().get(0).getOptimalKpiValues().get(0).getKpiName())));
		
	}
	
	@Test
	public void testGetSegmentStatusOK() throws Exception {

		final SegmentWithkpiResponse segmentWithkpiResponse = podam.manufacturePojo(SegmentWithkpiResponse.class);
		when(layoutVisualizationService.getSegmentStatus(any(), any(), any())).thenReturn(segmentWithkpiResponse);

		mockMvc.perform(get("/api/eAgro/v1/visualization/segmentstatus/1/sections/1/segments/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.segmentName", is(segmentWithkpiResponse.getSegmentName())))
				.andExpect(jsonPath("$.segmentDescription", is(segmentWithkpiResponse.getSegmentDescription())))
				.andExpect(jsonPath("$.zoneWithKpis[0].optimalKpiValues[0].kpiName",
						is(segmentWithkpiResponse.getZoneWithKpis().get(0).getOptimalKpiValues().get(0).getKpiName())));
	}
	
	@Test
	public void testGetZoneStatusOK() throws Exception {

		final SegmentZoneDetailsResponse segmentZoneDetailsResponse = podam
				.manufacturePojo(SegmentZoneDetailsResponse.class);
		final Zones zones = podam.manufacturePojo(Zones.class);
		final SensorsResponse sensorsResponse = podam.manufacturePojo(SensorsResponse.class);
		zones.setSensors(ImmutableList.of(sensorsResponse));
		segmentZoneDetailsResponse.setZones(ImmutableList.of(zones));
		when(layoutVisualizationService.getZoneStatus(any(), any(), any())).thenReturn(segmentZoneDetailsResponse);

		mockMvc.perform(get("/api/eAgro/v1/visualization/zonestatus/1/sections/1/segments/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.segmentName", is(segmentZoneDetailsResponse.getSegmentName())))
				.andExpect(jsonPath("$.segmentDescription", is(segmentZoneDetailsResponse.getSegmentDescription())))
				.andExpect(jsonPath("$.zones[0].key",
						is(segmentZoneDetailsResponse.getZones().get(0).getKey().toString())))
				.andExpect(jsonPath("$.zones[0].sensors[0].sensorDescription",
						is(segmentZoneDetailsResponse.getZones().get(0).getSensors().get(0).getSensorDescription())));

	}
	
	@Test
	public void testGetSegmentKpiChartOK() throws Exception {

		final Segmentkpichart segmentkpichart = podam.manufacturePojo(Segmentkpichart.class);
		final KpiValuesForTheCurrentSegment kpiValuesForTheCurrentSegment = podam
				.manufacturePojo(KpiValuesForTheCurrentSegment.class);
		segmentkpichart.setKpiValues(ImmutableList.of(kpiValuesForTheCurrentSegment));

		when(layoutVisualizationService.getSegmentKpiChartValues(any(), any(), any())).thenReturn(segmentkpichart);

		mockMvc.perform(get("/api/eAgro/v1/visualization/segmentkpichart/1/sections/1/segments/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.zoneType", is(segmentkpichart.getZoneType().toString())))
				.andExpect(jsonPath("$.kpiValues[0].kpiName", is(segmentkpichart.getKpiValues().get(0).getKpiName())));

	}
	
	
}

