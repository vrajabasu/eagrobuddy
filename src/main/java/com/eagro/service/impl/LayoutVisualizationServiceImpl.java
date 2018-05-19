package com.eagro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.repository.SegmentRepository;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.component.InternalLayoutDetailsService;
import com.eagro.service.dto.ActualKpi;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.OptimalKpiValueResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentsResponseDTO;
import com.eagro.service.dto.SegmentsResponseDTO.OverallThresholdstateEnum;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.SensorWithKpi;
import com.eagro.service.dto.ZonewithkpisResponseDTO;
import com.eagro.service.mapper.LayoutMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
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

	/** The section sensor mapping repository. */
	@Autowired
	public SectionSensorMappingRepository sectionSensorMappingRepository;

	/** The section sensor mapping mapper. */
	@Autowired
	public SectionSensorMappingMapper sectionSensorMappingMapper;

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
						sectionResponseDTO = fetchPerSectionDetails(layoutId, sectionDTO);
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
	 * Fetch per section details.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionDTO
	 *            the section DTO
	 * @return the sections response DTO
	 */
	private SectionsResponseDTO fetchPerSectionDetails(Long layoutId, SectionDTO sectionDTO) {
		SectionsResponseDTO sectionResponseDTO = new SectionsResponseDTO();
		if (sectionDTO != null) {
			// Enrich section details to the response
			sectionResponseDTO = layoutVisualizationMapper.sectiontoSectionResponse(sectionDTO);
			log.debug("Enrich Section value to Response : {} ", sectionResponseDTO);
			// bulk call for sectionSensorMapping
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

			// Retrieve the list of segments for specific section
			List<SegmentDTO> segmentDTOList = retrieveSegment(layoutId, sectionDTO);
			if (ServiceUtil.isNotEmptyResult(segmentDTOList)) {

				List<SegmentsResponseDTO> segmentsResponseDTOList = new ArrayList<>();
				for (SegmentDTO segment : segmentDTOList) {
					// Enrich segment details to the response
					SegmentsResponseDTO segmentsResponseDTO = layoutVisualizationMapper
							.segmenttoSegmentsResponseDTO(segment);
					// Retrieve sensor applicable for segment
					log.debug("Enriched segment details to the Response : {}", segmentsResponseDTO);

					Map<Long, SectionSensorMappingDTO> segmentSensorMap = internalLayoutDetailsService
							.identiySensorForCurrentSegment(currentSectionSensorMap, segment);
					// Retrieve Optimal KPI values
					if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
						Map<Long, List<KPIDTO>> sensorOptimalKPIMap = internalLayoutDetailsService
								.identifyOptimalKPIsForSensors(sectionDTO, segmentSensorMap, currentSectionKpi);
						if (sensorOptimalKPIMap != null && !sensorOptimalKPIMap.isEmpty()) {
							// Retrieve Actual KPI Values
							Map<Long, SensorDataDTO> sensorActualValueMap = internalLayoutDetailsService
									.identifyCurrentDataFromSensors(segmentSensorMap, currentSectionSensorData);
							// calculate overall threshold
							if (sensorActualValueMap != null && !sensorActualValueMap.isEmpty()) {
								OverallThresholdstateEnum thresholdState = internalLayoutDetailsService
										.calculateOverallThresholdState(sensorActualValueMap, sensorOptimalKPIMap);
								// Enrich the overall Threshold state to segment
								// response
								segmentsResponseDTO.setOverallThresholdstate(thresholdState);
								segmentsResponseDTOList.add(segmentsResponseDTO);
							}
						}
					}
				}
				// Enrich the segments list to response
				sectionResponseDTO.setSegments(segmentsResponseDTOList);
				log.debug("Enriched complete Data for section details to the Response : {}", sectionResponseDTO);
			}
		}
		return sectionResponseDTO;

	}

	/**
	 * Retrieve segment.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionDTO
	 *            the section DTO
	 * @return the list
	 */
	private List<SegmentDTO> retrieveSegment(Long layoutId, SectionDTO sectionDTO) {
		List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId, sectionDTO.getSectionId());
		log.debug("Segment details fetched from DB : {} ", segmentList);

		List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDTOList, layoutId);
		return segmentDTOList;
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
		return fetchPerSectionDetails(layoutId, sectionDTO);
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
	 * Gets the section based optimal kpi used to retrieve the optimal KPI
	 * values that are applicable for the request section for the kpi entity.
	 * The response incluce the ideal value for comaring against the actual
	 * sensor data for various kpis in real time..
	 *
	 * @param sectionId
	 *            the section id
	 * @param layoutId
	 *            TODO
	 * @return the section based optimal kpi
	 */
	@Override
	public SectionwithkpiResponseDTO getSectionBasedOptimalKpi(Long sectionId, Long layoutId) {
		// TODO Auto-generated method stub
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
			// Retrieve Sensor applicable for segment
			Map<Long, SectionSensorMappingDTO> segmentSensorMap = internalLayoutDetailsService
					.identiySensorForCurrentSegment(currentSectionSensorMap, segmentDto);
			Map<ZoneType, List<ActualKpi>> ZoneKPIMap = null;
			if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
				// Retrieve latest sensorData
				Map<Long, SensorDataDTO> sensorActualValueMap = internalLayoutDetailsService
						.identifyCurrentDataFromSensors(segmentSensorMap, currentSectionSensorData);
				ZoneKPIMap = determineCurrentConditionSegment(sensorActualValueMap, sensorList);

			}
			response.setSegmentName(segmentDto.getSegmentName());
			response.setSegmentDescription(segment.getSegmentDesc());

			List<ZonewithkpisResponseDTO> zoneWithKpis = new ArrayList<>();

			ZoneKPIMap.entrySet().forEach(map -> {
				ZonewithkpisResponseDTO zoneWithKpi = new ZonewithkpisResponseDTO();
				zoneWithKpi.setZoneType(map.getKey());
				List<OptimalKpiValueResponseDTO> optimalKpiList = new ArrayList<>();

				List<ActualKpi> optimalValueList = map.getValue();
				if (ServiceUtil.isNotEmptyResult(optimalValueList)) {
					optimalValueList.forEach(kpi -> {
						OptimalKpiValueResponseDTO optimalKpi = new OptimalKpiValueResponseDTO();
						optimalKpi.setKpiName(kpi.getParam1());
						optimalKpi.setOptimalValueRange(kpi.getParamValue1());
						optimalKpiList.add(optimalKpi);

					});
				}
				zoneWithKpi.setOptimalKpiValues(optimalKpiList);
				zoneWithKpis.add(zoneWithKpi);
			});

			response.setZoneWithKpis(zoneWithKpis);
			log.debug("Segment Status Response : {} ", response);
		}
		return response;
	}

	private Map<ZoneType, List<ActualKpi>> determineCurrentConditionSegment(
			Map<Long, SensorDataDTO> sensorActualValueMap, List<SectionSensorMappingDTO> sensorList) {
		Map<ZoneType, List<ActualKpi>> zoneTypeKpiMap = new HashMap<>();

		// Transpose logic

		sensorActualValueMap.values().forEach(sensorData -> {
			List<ActualKpi> actualKpiList = new ArrayList<>();
			sensorList.forEach(v -> {
				if (v.getSensorId().equals(sensorData.getSensorId())) {
					SensorDataDTO sensorActualVal = sensorActualValueMap.get(sensorData.getSensorId());
					ActualKpi actualKpi = new ActualKpi();
					actualKpi.setParam1(sensorActualVal.getParam1());
					actualKpi.setParamValue1(sensorActualVal.getParamValue1());
					actualKpi.setSensorId(sensorActualVal.getSensorId());
					actualKpi.setZoneType(v.getZoneType());
					actualKpiList.add(actualKpi);

					ActualKpi actualKpi1 = new ActualKpi();
					actualKpi1.setParam1(sensorActualVal.getParam2());
					actualKpi1.setParamValue1(sensorActualVal.getParamValue2());
					actualKpi1.setSensorId(sensorActualVal.getSensorId());
					actualKpi1.setZoneType(v.getZoneType());
					actualKpiList.add(actualKpi1);

					ActualKpi actualKpi2 = new ActualKpi();
					actualKpi2.setParam1(sensorActualVal.getParam3());
					actualKpi2.setParamValue1(sensorActualVal.getParamValue3());
					actualKpi2.setSensorId(sensorActualVal.getSensorId());
					actualKpi2.setZoneType(v.getZoneType());
					actualKpiList.add(actualKpi2);
					zoneTypeKpiMap.put(v.getZoneType(), actualKpiList);
				}
			});
			// Need to check with Veera
		});
		log.debug("Determine CurrentCondition Segment retruned the ZoneTypeWithActualKpiMap : {} ", zoneTypeKpiMap);
		return zoneTypeKpiMap;
	}

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
			sensorWithKpiResponse.setSegmentName(segmentDto.getSegmentName());
			sensorWithKpiResponse.setSegmentDescription(segmentDto.getSegmentDesc());
			sensorWithKpiResponse.setDate(
					sensorDataDto.getRecordedDateTime().getYear() + "/" + sensorDataDto.getRecordedDateTime().getMonth()
							+ "/" + sensorDataDto.getRecordedDateTime().getDayOfMonth());
			sensorWithKpiResponse.setTime(
					sensorDataDto.getRecordedDateTime().getHour() + ":" + sensorDataDto.getRecordedDateTime().getHour()
							+ ":" + sensorDataDto.getRecordedDateTime().getMinute());
			sensorWithKpiResponse.setSensorId(sensorId);
			sensorWithKpiResponse.setZoneType(sensor.getZoneType());
			List<OptimalKpiValueResponseDTO> optimalValueList = new ArrayList<>();
			OptimalKpiValueResponseDTO optimalValue = new OptimalKpiValueResponseDTO();
			optimalValue.setKpiName(sensorDataDto.getParam1());
			optimalValue.setOptimalValueRange(sensorDataDto.getParamValue1());
			optimalValueList.add(optimalValue);
			OptimalKpiValueResponseDTO optimalValue1 = new OptimalKpiValueResponseDTO();
			optimalValue1.setKpiName(sensorDataDto.getParam2());
			optimalValue1.setOptimalValueRange(sensorDataDto.getParamValue2());
			optimalValueList.add(optimalValue1);
			OptimalKpiValueResponseDTO optimalValue2 = new OptimalKpiValueResponseDTO();
			optimalValue2.setKpiName(sensorDataDto.getParam3());
			optimalValue2.setOptimalValueRange(sensorDataDto.getParamValue3());
			optimalValueList.add(optimalValue2);
			sensorWithKpiResponse.setKpiValues(optimalValueList);
		}

		return sensorWithKpiResponse;
	}

}
