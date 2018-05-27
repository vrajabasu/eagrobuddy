package com.eagro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.Layout;
import com.eagro.entities.Section;
import com.eagro.entities.Segment;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.LayoutRepository;
import com.eagro.repository.SectionRepository;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.component.InternalLayoutDetailsService;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentZoneDetailsResponse;
import com.eagro.service.dto.Segmentkpichart;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.SensorWithKpi;
import com.eagro.service.mapper.LayoutMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionMapper;
import com.eagro.service.mapper.SegmentMapper;
import com.eagro.service.utils.ServiceUtil;

/**
 * The Class LayoutVisualizationServiceImpl.
 */
@Service
@Transactional
public class LayoutVisualizationServiceImpl implements LayoutVisualizationService {

	/** The Constant LAYOUT_NOT_AVAILABLE. */
	private static final String LAYOUT_NOT_AVAILABLE = "Layout Details is not Available";
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LayoutVisualizationServiceImpl.class);

	/** The layout repository. */
	@Autowired
	public LayoutRepository layoutRepository;

	/** The layout mapper. */
	@Autowired
	public LayoutMapper layoutMapper;

	/** The segment details service. */
	@Autowired
	public InternalLayoutDetailsService internalLayoutDetailsService;

	/** The layout visualization mapper. */
	@Autowired
	public LayoutVisualizationMapper layoutVisualizationMapper;

	/** The section repository. */
	@Autowired
	public SectionRepository sectionRepository;

	/** The section mapper. */
	@Autowired
	public SectionMapper sectionMapper;

	/** The segment repository. */
	@Autowired
	public SegmentRepository segmentRepository;

	/** The segment mapper. */
	@Autowired
	public SegmentMapper segmentMapper;

	/**
	 * Generate layout detail info used to retrieve overall layout visualization
	 * showing sections and segments with in the sections, as well current
	 * status of each section represented by colour coding. Green indicates
	 * segment is under normal condition. Yellow indicates that the section is
	 * performing with in the reference range but KPIs are outside deviation
	 * range specified and propability of moving out of reference range is
	 * likely. RED indicates that reading from sensors are outside optimal
	 * reference range for that particular segment.
	 *
	 * @param layoutId
	 *            the layout id
	 * @return the layout response DTO
	 */
	@Override
	public LayoutResponseDTO getLayoutDetails(Long layoutId) {
		// Fetch layout information from layout table
		log.debug("Request to get Layout  : {}", layoutId);
		Layout layout = layoutRepository.findOne(layoutId);
		log.debug("layout information fetched from DB: {}", layout);
		LayoutDTO layoutDTO = layoutMapper.toDto(layout);
		// Enrich layout information in response
		LayoutResponseDTO layoutResponseDTO = new LayoutResponseDTO();
		if (layoutDTO == null) {
			log.error(LAYOUT_NOT_AVAILABLE);
		} else {
			layoutResponseDTO = layoutVisualizationMapper.layouttoLayoutResponse(layoutDTO);
			log.debug("Enriched the layout information to the Response : {}", layoutResponseDTO);

			// Fetch all section info
			List<Section> sectionList = sectionRepository.findByLayoutId(layoutId);
			log.debug("section information fetched from DB: {}", sectionList);

			List<SectionDTO> sectionDTOList = sectionMapper.toDto(sectionList);

			log.debug("List of section information : {} for layout : {}", sectionDTOList, layoutId);
			// Enrich section info to response
			if (layoutResponseDTO != null && ServiceUtil.isNotEmptyResult(sectionDTOList)) {
				List<SectionsResponseDTO> sectionResponseDTOList = new ArrayList<>();
				// Iterate the section
				for (SectionDTO sectionDTO : sectionDTOList) {
					SectionsResponseDTO sectionResponseDTO = null;
					if (sectionDTO != null) {
						sectionResponseDTO = internalLayoutDetailsService.fetchPerSectionDetails(layoutId, sectionDTO);
					}
					sectionResponseDTOList.add(sectionResponseDTO);
				}
				// Enrich the sections list to response
				layoutResponseDTO.setSections(sectionResponseDTOList);
			}
		}
		return layoutResponseDTO;
	}

	/**
	 * Gets the section details info used to provide current status of the
	 * section based on the request section id within a particular layout. The
	 * color coding of each segment within the section represents the current
	 * condition of the segment. Green indicates segment is under normal
	 * condition. Yellow indicates that the section is performing with in the
	 * reference range but KPIs are outside deviation range specified and
	 * propability of moving out of reference range is likely. RED indicates
	 * that reading from sensors are outside optimal reference range for that
	 * particular segment.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @return the section details
	 */
	@Override
	public SectionsResponseDTO getSectionDetails(Long layoutId, Long sectionId) {
		log.debug("Request to get Section details based on layoutId  : {} and sectionId : {}", layoutId, sectionId);
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);
		return internalLayoutDetailsService.fetchPerSectionDetails(layoutId, sectionDTO);
	}

	/**
	 * Gets the section based optimal kpi used to retrieve the optimal KPI
	 * values that are applicable for the request section for the kpi entity.
	 * The response incluce the ideal value for comaring against the actual
	 * sensor data for various kpis in real time..
	 *
	 * @param sectionId
	 *            the section id
	 * @param layoutId
	 *            the layout id
	 * @return the section based optimal kpi
	 */
	@Override
	public SectionwithkpiResponseDTO getSectionBasedOptimalKpi(Long sectionId, Long layoutId) {
		log.debug("Request to get Section Based Optimal Kpi values by layoutId  : {} and sectionId : {}", layoutId,
				sectionId);
		// To fetch sectionDetails
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);

		Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = internalLayoutDetailsService
				.getAllSensorsForSection(sectionDTO);
		List<SectionSensorMappingDTO> sectionSensorMappingDTOList = currentSectionSensorMap.get(sectionDTO);
		List<KPIDTO> currentSectionKpi = internalLayoutDetailsService.retrieveKpiForCurrentSection(layoutId,
				sectionDTO.getSectionId());

		// create map based on zoneType
		Map<ZoneType, List<KPIDTO>> zoneBasedKpiValueMap = currentSectionKpi.stream()
				.collect(Collectors.groupingBy(k -> k.getZoneType()));
		SectionwithkpiResponseDTO sectionWithKpiResponseDTO = new SectionwithkpiResponseDTO();

		if (ServiceUtil.isNotEmptyResult(sectionSensorMappingDTOList)) {
			internalLayoutDetailsService.getSectionBasedKpiValues(sectionDTO, sectionSensorMappingDTOList,
					zoneBasedKpiValueMap, sectionWithKpiResponseDTO);
		}
		return sectionWithKpiResponseDTO;
	}

	/**
	 * This service endpoint is used to retrieve the current sensor data from
	 * cloud data source for a particular sensor.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @param sensorId
	 *            the sensor id
	 * @return the sensor status
	 */
	@Override
	public SegmentWithkpiResponse getSegmentStatus(Long layoutId, Long sectionId, Long segmentId) {
		log.debug("Request to get Segment current condition by layoutId  : {} and sectionId : {}", layoutId, sectionId);
		// Retrieve senction Detail from section
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);
		// Retreive all sensors from sectionsensormapping
		Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = internalLayoutDetailsService
				.getAllSensorsForSection(sectionDTO);

		List<SectionSensorMappingDTO> sensorList = new ArrayList<>();
		List<SensorDataDTO> currentSectionSensorData = new ArrayList<>();
		if (!currentSectionSensorMap.entrySet().isEmpty()) {
			sensorList = currentSectionSensorMap.entrySet().iterator().next().getValue();
			currentSectionSensorData = internalLayoutDetailsService.retrieveSensorDataList(layoutId, sensorList);
		}
		log.debug("currentSectionSensorData : {}  for currentSection : {}", currentSectionSensorData, sectionId);

		// Retrieve segmentDetails
		Segment segment = segmentRepository.findByLayoutIdAndSectionIdAndSegmentId(layoutId, sectionId, segmentId);
		SegmentDTO segmentDto = segmentMapper.toDto(segment);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDto, layoutId);
		SegmentWithkpiResponse response = new SegmentWithkpiResponse();
		if (segmentDto != null) {
			internalLayoutDetailsService.retrieveSegmentWithKpi(currentSectionSensorMap, sensorList,
					currentSectionSensorData, segment, segmentDto, response);
		}
		return response;
	}

	/**
	 * This service endpoint is used to retrieve the current sensor data from
	 * cloud data source for a particular sensor.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @param sensorId
	 *            the sensor id
	 * @return the sensor status
	 */
	@Override
	public SensorWithKpi getSensorStatus(Long layoutId, Long sectionId, Long segmentId, Long sensorId) {

		// Retrieve segmentDetails
		Segment segment = segmentRepository.findByLayoutIdAndSectionIdAndSegmentId(layoutId, sectionId, segmentId);
		SegmentDTO segmentDto = segmentMapper.toDto(segment);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDto, layoutId);
		SensorDataDTO sensorDataDto = internalLayoutDetailsService.retrieveSensorData(layoutId, sensorId);
		log.debug("Sensor Data : {} for layoutId :{] and sensorId:{}", sensorDataDto, layoutId, sensorId);
		SectionSensorMappingDTO sensor = internalLayoutDetailsService.getSensorByIds(layoutId, sectionId, sensorId);
		log.debug("Sensor : {} for layoutId :{] , sectionID :{} and sensorId:{}", sensorDataDto, layoutId, sensorId);
		SensorWithKpi sensorWithKpiResponse = new SensorWithKpi();
		if (segmentDto != null && sensorDataDto != null && sensor != null) {
			internalLayoutDetailsService.setSensorStatusResult(sensorId, segmentDto, sensorDataDto, sensor,
					sensorWithKpiResponse);
		}

		return sensorWithKpiResponse;
	}

	/**
	 * Retrieve section basic info.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @return the section DTO
	 */
	private SectionDTO retrieveSectionBasicInfo(Long layoutId, Long sectionId) {
		Section section = sectionRepository.findByLayoutIdAndSectionId(layoutId, sectionId);
		SectionDTO sectionDTO = sectionMapper.toDto(section);
		return sectionDTO;
	}

	/**
	 * This service endpoint is used to retrieve the current status of the farm
	 * categorized by different zone types. The reponse also includes current
	 * threshold state identified by different color codes. Green indicates
	 * segment is under normal condition. Yellow indicates that the section is
	 * performing with in the reference range but KPIs are outside deviation
	 * range specified and propability of moving out of reference range is
	 * likely. RED indicates that reading from sensors are outside optimal
	 * reference range for that particular segment.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @return the zone status
	 */
	@Override
	public SegmentZoneDetailsResponse getZoneStatus(Long layoutId, Long sectionId, Long segmentId) {

		log.debug("Request to get Zone details based on layoutId  : {} , sectionId : {} and  segmentId : {}", layoutId,
				sectionId, segmentId);
		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);
		Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = internalLayoutDetailsService
				.getAllSensorsForSection(sectionDTO);
		log.debug("SensorList : {} for specific section :{} from SectionSensorMapping", currentSectionSensorMap,
				sectionDTO.getSectionId());

		List<KPIDTO> currentSectionKpi = internalLayoutDetailsService.retrieveKpiForCurrentSection(layoutId,
				sectionDTO.getSectionId());
		log.debug("Optimal KPI Values : {}  for currentSection : {}", currentSectionKpi, sectionDTO.getSectionId());

		List<SectionSensorMappingDTO> sensorList = new ArrayList<>();
		List<SensorDataDTO> currentSectionSensorData = new ArrayList<>();
		if (!currentSectionSensorMap.entrySet().isEmpty()) {
			sensorList = currentSectionSensorMap.entrySet().iterator().next().getValue();
			currentSectionSensorData = internalLayoutDetailsService.retrieveSensorDataList(layoutId, sensorList);
		}
		log.debug("currentSectionSensorData : {}  for currentSection : {}", currentSectionSensorData,
				sectionDTO.getSectionId());
		SegmentZoneDetailsResponse segmentZoneDetails = new SegmentZoneDetailsResponse();

		segmentZoneDetails.setSectionName(sectionDTO.getSectionName());
		segmentZoneDetails.setSectionDescription(sectionDTO.getSectionDesc());
		// Need to check with Veera
		segmentZoneDetails.setSectionX(sectionDTO.getStartX());
		segmentZoneDetails.setSectionY(sectionDTO.getStartY());

		SegmentDTO segmentDTO = internalLayoutDetailsService.retrieveSegment(layoutId, sectionDTO.getSectionId(),
				segmentId);

		if (segmentDTO != null) {
			internalLayoutDetailsService.fetchZoneStatus(sectionDTO, currentSectionSensorMap, currentSectionKpi, currentSectionSensorData,
					segmentZoneDetails, segmentDTO);

		}
		return segmentZoneDetails;
	}

	@Override
	public Segmentkpichart getSegmentKpiChartValues(Long layoutId, Long sectionId, Long segmentId) {
		// TODO Auto-generated method stub

		SectionDTO sectionDTO = retrieveSectionBasicInfo(layoutId, sectionId);
		// Retreive all sensors from sectionsensormapping
		Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = internalLayoutDetailsService
				.getAllSensorsForSection(sectionDTO);

		List<SectionSensorMappingDTO> sensorList = new ArrayList<>();
		List<SensorDataDTO> currentSectionSensorData = new ArrayList<>();
		if (!currentSectionSensorMap.entrySet().isEmpty()) {
			sensorList = currentSectionSensorMap.entrySet().iterator().next().getValue();
			currentSectionSensorData = internalLayoutDetailsService.retrieveSensorDataList(layoutId, sensorList);
		}
		log.debug("currentSectionSensorData : {}  for currentSection : {}", currentSectionSensorData, sectionId);

		// Retrieve segmentDetails
		Segment segment = segmentRepository.findByLayoutIdAndSectionIdAndSegmentId(layoutId, sectionId, segmentId);
		SegmentDTO segmentDto = segmentMapper.toDto(segment);

		// Retrieve Sensor applicable for segment
		Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap = internalLayoutDetailsService
				.identiySensorForCurrentSegment(currentSectionSensorMap, segmentDto);
		// Retrieve historical kpi by zones
		Segmentkpichart segmentKpiChartValues = new Segmentkpichart();
		if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
			internalLayoutDetailsService.findHistoricalKpiValues(segmentId, segmentSensorMap, segmentKpiChartValues);
		}

		return segmentKpiChartValues;
	}

}
