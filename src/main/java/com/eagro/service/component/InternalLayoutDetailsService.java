package com.eagro.service.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.KPI;
import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.Segment;
import com.eagro.entities.Sensor;
import com.eagro.entities.SensorCoverageRange;
import com.eagro.entities.SensorData;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.KPIRepository;
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.repository.SegmentRepository;
import com.eagro.repository.SensorCoverageRangeRepository;
import com.eagro.repository.SensorDataRepository;
import com.eagro.repository.SensorRepository;
import com.eagro.service.dto.ActualKpi;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.OptimalKpiValueResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentZoneDetailsResponse;
import com.eagro.service.dto.Segmentkpichart;
import com.eagro.service.dto.SegmentsResponseDTO;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.dto.SensorDTO;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.SensorWithKpi;
import com.eagro.service.dto.SensorsResponse;
import com.eagro.service.dto.ZoneStatus;
import com.eagro.service.dto.Zones;
import com.eagro.service.dto.ZonewithkpisResponseDTO;
import com.eagro.service.dto.enumeration.OverallThresholdstateEnum;
import com.eagro.service.impl.LayoutVisualizationServiceImpl;
import com.eagro.service.mapper.KPIMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
import com.eagro.service.mapper.SegmentMapper;
import com.eagro.service.mapper.SensorCoverageRangeMapper;
import com.eagro.service.mapper.SensorDataMapper;
import com.eagro.service.mapper.SensorMapper;
import com.eagro.service.utils.ServiceUtil;

import io.swagger.model.KpiValuesForTheCurrentSegment;

/**
 * The Class SegmentDetailsService.
 */
@Component
@Transactional
public class InternalLayoutDetailsService {

	/** The Constant LIGHT. */
	private static final String LIGHT = "L";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LayoutVisualizationServiceImpl.class);

	/** The layout visualization mapper. */
	@Autowired
	public LayoutVisualizationMapper layoutVisualizationMapper;

	/** The section sensor mapping repository. */
	@Autowired
	public SectionSensorMappingRepository sectionSensorMappingRepository;

	/** The section sensor mapping mapper. */
	@Autowired
	public SectionSensorMappingMapper sectionSensorMappingMapper;

	/** The sensor coverage range repository. */
	@Autowired
	public SensorCoverageRangeRepository sensorCoverageRangeRepository;

	/** The kpi respository. */
	@Autowired
	public KPIRepository kpiRespository;

	/** The sensor data repository. */
	@Autowired
	public SensorDataRepository sensorDataRepository;

	/** The sensor data mapper. */
	@Autowired
	public SensorDataMapper sensorDataMapper;

	/** The kpi mapper. */
	@Autowired
	public KPIMapper kpiMapper;

	/** The sensor coverage range mapper. */
	@Autowired
	public SensorCoverageRangeMapper sensorCoverageRangeMapper;

	/** The segment repository. */
	@Autowired
	public SegmentRepository segmentRepository;

	/** The segment mapper. */
	@Autowired
	public SegmentMapper segmentMapper;

	/** The sensor repository. */
	@Autowired
	public SensorRepository sensorRepository;

	/** The sensor mapper. */
	@Autowired
	public SensorMapper sensorMapper;

	/**
	 * Calculate overall threshold state by comparing actual sensor data and
	 * optimal sensor data.
	 *
	 * @param sensorActualValueMap
	 *            the sensor actual value map
	 * @param sensorOptimalKPIMap
	 *            the sensor optimal KPI map
	 * @return the overall thresholdstate enum
	 */
	public OverallThresholdstateEnum calculateOverallThresholdState(Map<Long, SensorDataDTO> sensorActualValueMap,
			Map<Long, List<KPIDTO>> sensorOptimalKPIMap) {
		List<OverallThresholdstateEnum> currentThresholdStateList = new ArrayList<>();
		// Iterate sensorActualValue Map
		sensorActualValueMap.values().forEach(sensor -> {
			OverallThresholdstateEnum currentSensorState = OverallThresholdstateEnum.NORMAL;
			// For each Kpi from sensor check the current KPI is not light then
			// consider
			if (!(LIGHT.equalsIgnoreCase(sensor.getParam1()))) {
				List<KPIDTO> sensorOptimalKpi = sensorOptimalKPIMap.get(sensor.getSensorId());
				if (ServiceUtil.isNotEmptyResult(sensorOptimalKpi)) {
					for (KPIDTO kpi : sensorOptimalKpi) {
						if (kpi != null && ServiceUtil.isNotEmpty(kpi.getKpiName())) {
							if (kpi.getKpiName().equals(sensor.getParam1())) {
								// Logic to check within reference range and
								// deviation range
								Double interimVar = sensor.getParamValue1();
								if (kpi.getLowerRefLimit() <= interimVar && kpi.getUpperRefLimit() >= interimVar) {
									if ((kpi.getLowerRefLimit() + kpi.getDeviationRange() <= interimVar)
											&& ((kpi.getUpperRefLimit() - kpi.getDeviationRange()) >= interimVar)) {
										currentSensorState = OverallThresholdstateEnum.NORMAL;
									} else {
										currentSensorState = OverallThresholdstateEnum.EXCEEDING_SOON;
									}
								} else {
									currentSensorState = OverallThresholdstateEnum.EXCEEDED;
								}
							}
						}
					}
				}
				currentThresholdStateList.add(currentSensorState);
			}
			log.trace("The sensorId : {} hold the threshold state as : {}", sensor.getSensorId(), currentSensorState);

		});

		return calculateThresholdBasedOnPrecendence(currentThresholdStateList);
	}

	public Map<Long, OverallThresholdstateEnum> calculateThresholdStateOfSensors(
			Map<Long, SensorDataDTO> sensorActualValueMap, Map<Long, List<KPIDTO>> sensorOptimalKPIMap) {
		log.debug("Individual ThresholdState Entry");
		Map<Long, OverallThresholdstateEnum> sensorThresholdState = new HashMap<Long, OverallThresholdstateEnum>();
		sensorActualValueMap.values().forEach(sensor -> {
			OverallThresholdstateEnum currentSensorState = OverallThresholdstateEnum.NORMAL;
			// For each Kpi from sensor check the current KPI is not light then
			// consider
			if (!(LIGHT.equalsIgnoreCase(sensor.getParam1()))) {
				List<KPIDTO> sensorOptimalKpi = sensorOptimalKPIMap.get(sensor.getSensorId());
				if (ServiceUtil.isNotEmptyResult(sensorOptimalKpi)) {
					for (KPIDTO kpi : sensorOptimalKpi) {
						if (kpi != null && ServiceUtil.isNotEmpty(kpi.getKpiName())) {
							if (kpi.getKpiName().equals(sensor.getParam1())) {
								// Logic to check within reference range and
								// deviation range
								Double interimVar = sensor.getParamValue1();
								if (kpi.getLowerRefLimit() <= interimVar && kpi.getUpperRefLimit() >= interimVar) {
									if ((kpi.getLowerRefLimit() + kpi.getDeviationRange() <= interimVar)
											&& ((kpi.getUpperRefLimit() - kpi.getDeviationRange()) >= interimVar)) {
										currentSensorState = OverallThresholdstateEnum.NORMAL;
									} else {
										currentSensorState = OverallThresholdstateEnum.EXCEEDING_SOON;
									}
								} else {
									currentSensorState = OverallThresholdstateEnum.EXCEEDED;
								}
							}
						}
					}
				}
				sensorThresholdState.put(sensor.getSensorId(), currentSensorState);
			}
			log.debug("The sensorId : {} hold the threshold state as : {}", sensor.getSensorId(), currentSensorState);

		});
		log.debug("The Threshold state Sensor Map : {}", sensorThresholdState);
		return sensorThresholdState;

	}

	/**
	 * Calculate overall threshold state from list of threshold state for per
	 * segment based on precedence.
	 *
	 * @param currentThresholdStateList
	 *            the current threshold state list
	 * @return the overall thresholdstate enum
	 */
	private OverallThresholdstateEnum calculateThresholdBasedOnPrecendence(
			List<OverallThresholdstateEnum> currentThresholdStateList) {
		OverallThresholdstateEnum thresholdState = OverallThresholdstateEnum.NORMAL;
		for (OverallThresholdstateEnum currentState : currentThresholdStateList) {
			if (!currentState.equals(OverallThresholdstateEnum.NORMAL)) {
				if (currentState.equals(OverallThresholdstateEnum.EXCEEDING_SOON)
						&& (thresholdState.equals(OverallThresholdstateEnum.NORMAL))) {
					thresholdState = OverallThresholdstateEnum.EXCEEDING_SOON;
				}
				if (currentState.equals(OverallThresholdstateEnum.EXCEEDED)) {
					thresholdState = OverallThresholdstateEnum.EXCEEDED;
				}
			}
		}
		log.trace("The Overall Threshold for the specific segment :{}", thresholdState);
		return thresholdState;

	}

	/**
	 * Retrieve current data from sensors by actual KPI values i.e., latest
	 * sensor data at that point in time from cloud data source for applicable
	 * sensors retrieved from retrieveOptimalKPIsForSensors method.
	 *
	 * @param segmentSensorMap
	 *            the segment sensor map
	 * @param currentSectionSensorData
	 *            the current section sensor Data from bulk call
	 * @return the map
	 */
	public Map<Long, SensorDataDTO> identifyCurrentDataFromSensors(
			Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap, List<SensorDataDTO> currentSectionSensorData) {
		Map<Long, SensorDataDTO> sensorActualValueMap = new HashMap<>();

		List<SectionSensorMappingDTO> sectionSensorMappingList = segmentSensorMap.values().stream()
				.flatMap(x -> x.stream()).collect(Collectors.toList());
		List<Long> sensorIdList = sectionSensorMappingList.stream().map(v -> v.getSensorId())
				.collect(Collectors.toList());

		Map<Long, SensorDataDTO> currentSensorDataMap = currentSectionSensorData.stream()
				.collect(Collectors.toMap(SensorDataDTO::getSensorId, Function.identity()));
		if (currentSensorDataMap != null && ServiceUtil.isNotEmptyResult(sensorIdList)) {
			sensorIdList.forEach(sensorId -> {
				sensorActualValueMap.put(sensorId, currentSensorDataMap.get(sensorId));
			});
		}
		log.debug("The Actual KPI values from Sensor Latest Data : {} ", sensorActualValueMap.values());
		return sensorActualValueMap;
	}

	/**
	 * Identify optimal KPI is for sensors for the current section.
	 *
	 * @param section
	 *            the section
	 * @param segmentSensorMap
	 *            the segment sensor map
	 * @param currentSectionKpi
	 *            the current section kpi
	 * @return the map
	 */
	public Map<Long, List<KPIDTO>> identifyOptimalKPIsForSensors(SectionDTO section,
			Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap, List<KPIDTO> currentSectionKpi) {
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = new HashMap<>();
		Long sectionId = section.getSectionId();
		Long layoutId = section.getLayoutId();
		List<SectionSensorMappingDTO> sectionSensorMappingList = segmentSensorMap.values().stream()
				.flatMap(x -> x.stream()).collect(Collectors.toList());
		Map<ZoneType, List<Long>> sensorZoneBasedMap = sectionSensorMappingList.stream()
				.filter(v -> sectionId.equals(v.getSectionId()) && layoutId.equals(v.getLayoutId()))
				.collect(Collectors.groupingBy(SectionSensorMappingDTO::getZoneType,
						Collectors.mapping(SectionSensorMappingDTO::getSensorId, Collectors.toList())));

		Map<ZoneType, List<KPIDTO>> zoneBasedKPIMap = currentSectionKpi.stream()
				.collect(Collectors.groupingBy(KPIDTO::getZoneType));
		if (zoneBasedKPIMap != null && sensorZoneBasedMap != null) {
			sensorZoneBasedMap.keySet().forEach(v -> {
				sensorZoneBasedMap.get(v).forEach(sensorId -> {
					sensorOptimalKPIMap.put(sensorId, zoneBasedKPIMap.get(v));
				});
			});
		}
		log.debug("Optimal value Map  : {} with sensorId key", sensorOptimalKPIMap);
		return sensorOptimalKPIMap;
	}

	/**
	 * This method used to Retrieve kpi details for current layout, section and
	 * zonetype.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param zoneType
	 *            the zone type
	 * @return the list
	 */
	public List<KPIDTO> retrieveKpi(Long layoutId, Long sectionId, ZoneType zoneType) {

		List<KPI> kpiList = kpiRespository.findByLayoutIdAndSectionIdAndZoneType(layoutId, sectionId, zoneType);
		List<KPIDTO> kpiDTOList = kpiMapper.toDto(kpiList);

		return kpiDTOList;
	}

	/**
	 * Retrieve list of sensors applicable for current segment.
	 *
	 * @param currentSectionSensorMap
	 *            the section DTO
	 * @param segmentDTO
	 *            the segment DTO
	 * @param sensorCoverageRangeList
	 *            TODO
	 * @return the map
	 */
	public Map<Long, List<SectionSensorMappingDTO>> identiySensorForCurrentSegment(
			Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap, SegmentDTO segmentDTO,
			Map<Long, List<SensorCoverageRangeDTO>> sensorCoverageRangeMap) {

		Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap = new HashMap<>();
		// validate whether the coordinates of the sensor falls within the
		// segment coordinates
		List<SectionSensorMappingDTO> interimSectionBasedSensorList = new ArrayList<>();
		if (!currentSectionSensorMap.entrySet().isEmpty() && segmentDTO != null) {
			Entry<SectionDTO, List<SectionSensorMappingDTO>> singleEntryMap = currentSectionSensorMap.entrySet()
					.iterator().next();
			SectionDTO sectionDTO = singleEntryMap.getKey();
			singleEntryMap.getValue().forEach(sensor -> {
				SectionSensorMappingDTO interimSectionBasedSensor = null;
				if (checkSensorWithInSegRange(segmentDTO, sensor, sectionDTO)) {
					interimSectionBasedSensor = layoutVisualizationMapper.sensorToSensor(sensor);
					log.debug(
							"The interim sensor :{} is within segment Range and added to segmentSensorMap with segmentId: {}",
							interimSectionBasedSensor, segmentDTO.getSegmentId());
					interimSectionBasedSensorList.add(interimSectionBasedSensor);
					// segmentSensorMap.put(segmentDTO.getSegmentId(),
					// interimSectionBasedSensor);

				}
			});
			log.debug("Before considering the Water Sesnor, the interimSectionBasedSensorList : {}",
					interimSectionBasedSensorList);
			// Logic to consider Water sensorCoverageRange
			List<SectionSensorMappingDTO> sectionSensorList = currentSectionSensorMap.values().stream()
					.flatMap(v -> v.stream()).collect(Collectors.toList());
			if (ServiceUtil.isNotEmptyResult(sectionSensorList)) {
				sectionSensorList.forEach(sectionSensorMapping -> {
					// retrieve setionSenorMapping = Zonetype checking whether
					// water sensor coverage range is within the range
					if (sectionSensorMapping != null && sectionSensorMapping.getZoneType() == ZoneType.W) {
						// Retrieve sensorCoveragerange from entity based on
						// layout,section and sensorId
						log.debug("Water sensor for the section: {}", sectionSensorMapping);
						if (sensorCoverageRangeMap != null) {
							if (sensorCoverageRangeMap.get(sectionSensorMapping.getSensorId()) != null) {
								interimSectionBasedSensorList.add(sectionSensorMapping);
							}
						}
					}
				});
			}
			segmentSensorMap.put(segmentDTO.getSegmentId(), interimSectionBasedSensorList);
		}
		log.debug("The segment sensor Map : {}  contains sensor coverage range is within segment range",
				segmentSensorMap);
		return segmentSensorMap;
	}

	public List<SensorCoverageRangeDTO> getSensorCoverageByIds(Long layoutId, Long sectionId, List<Long> sensorId) {
		List<SensorCoverageRange> currentSensorCoverageRangeList = sensorCoverageRangeRepository
				.findByLayoutIdAndSectionIdAndSensorId(layoutId, sectionId, sensorId);
		log.debug("Retrieve current SensorRange");
		List<SensorCoverageRangeDTO> sensorCoverageRangeList = sensorCoverageRangeMapper
				.toDto(currentSensorCoverageRangeList);
		log.debug("sensorCoverageRange : {}", sensorCoverageRangeList);
		return sensorCoverageRangeList;
	}

	public SectionSensorMappingDTO getSensorByIds(Long layoutId, Long sectionId, Long sensorId) {
		SectionSensorMapping sensor = sectionSensorMappingRepository.findByLayoutIdAndSectionIdAndSensorId(layoutId,
				sectionId, sensorId);
		SectionSensorMappingDTO sectionSensor = sectionSensorMappingMapper.toDto(sensor);
		return sectionSensor;
	}

	/**
	 * This method used to Gets the all sensors for current section in bulk
	 * manner.
	 *
	 * @param sectionDTO
	 *            the section DTO
	 * @return the all sensors for section
	 */
	public Map<SectionDTO, List<SectionSensorMappingDTO>> getAllSensorsForSection(SectionDTO sectionDTO) {
		// Retrieve all sensor for the section from sectionsensorMapping entity
		// using layoutId and sectionId
		List<SectionSensorMapping> sensorList = sectionSensorMappingRepository
				.findBySectionIdAndLayoutId(sectionDTO.getLayoutId(), sectionDTO.getSectionId());
		log.debug("sensorList Data Fetched from sectionSensorMappingRepository DB : {} for layoutId : {}", sensorList,
				sectionDTO.getLayoutId());

		// Mapper entityToDTO
		List<SectionSensorMappingDTO> sensorDTOList = sectionSensorMappingMapper.toDto(sensorList);

		log.debug("sensorList Data : {} for layoutId : {}", sensorDTOList, sectionDTO.getLayoutId());
		Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = new HashMap<>();
		currentSectionSensorMap.put(sectionDTO, sensorDTOList);
		return currentSectionSensorMap;
	}

	/**
	 * Check whether the sensor coverage range is with in the segment
	 * coordinates range.
	 *
	 * @param segmentDTO
	 *            the segment DTO
	 * @param currentSensorCoverageRangeDTO
	 *            the current sensor coverage range DTO
	 * @param sectionDTO
	 *            the section DTO
	 * @return true, if successful
	 */
	private boolean checkSensorCoverageWithinsegRange(SegmentDTO segmentDTO,
			SensorCoverageRangeDTO currentSensorCoverageRangeDTO, SectionDTO sectionDTO) {
		boolean isNotOverlapping = false;
		Double sensorStartX = currentSensorCoverageRangeDTO.getStartX() + sectionDTO.getStartX();
		Double sensorStartY = currentSensorCoverageRangeDTO.getStartY() + sectionDTO.getStartY();
		Double sensorEndX = currentSensorCoverageRangeDTO.getEndX() + sectionDTO.getEndX();
		Double sensorEndY = currentSensorCoverageRangeDTO.getEndY() + sectionDTO.getEndY();
		Double segmentStartX = sectionDTO.getStartX() + segmentDTO.getStartX();
		Double segmentEndX = sectionDTO.getEndX() + segmentDTO.getEndX();
		Double segmentStartY = sectionDTO.getStartY() + segmentDTO.getStartY();
		Double segmentEndY = sectionDTO.getEndY() + segmentDTO.getEndY();

		log.debug("Absolute value for sensorCoverage startX :{} endX : {} startY: {} endY:{} ", sensorStartX,
				sensorEndX, sensorStartY, sensorEndY);
		log.debug("Segment EndX : {} and EndY : {}", segmentDTO.getEndX(), segmentDTO.getEndY());
		isNotOverlapping = (segmentStartX > sensorEndX) || (segmentEndY > sensorStartY) || (sensorEndX > segmentEndX)
				|| (sensorEndY > segmentStartY);

		if (isNotOverlapping) {
			log.debug("The sensor is not in the segment Range");
		} else {
			log.debug("The sensor falls with in the segment Range");
		}
		return isNotOverlapping;
	}

	/**
	 * Check sensor the corordinates Posx and Posy falls with in segment
	 * coordinates x and y range.
	 *
	 * @param segment
	 *            the segment
	 * @param sensor
	 *            the sensor
	 * @param sectionDTO
	 *            the section DTO
	 * @return true, if successful
	 */
	private boolean checkSensorWithInSegRange(SegmentDTO segment, SectionSensorMappingDTO sensor,
			SectionDTO sectionDTO) {
		boolean isWithinRange = false;
		Double posX = sectionDTO.getStartX() + sensor.getPosX();
		Double posY = sectionDTO.getStartY() + sensor.getPosY();
		Double segmentStartX = sectionDTO.getStartX() + segment.getStartX();
		Double segmentEndX = sectionDTO.getEndX() + segment.getEndX();
		Double segmentStartY = sectionDTO.getStartY() + segment.getStartY();
		Double segmentEndY = sectionDTO.getEndY() + segment.getEndY();
		log.debug("Absolute Value for sensor PosX : {} and PosY : {}", posX, posY);
		log.debug("Segment startX : {} endX : {} and startY : {} endY : {}", segmentStartX, segmentEndX, segmentStartY,
				segmentEndY);
		isWithinRange = (posX >= segmentStartX) && (posX <= segmentEndX) && (posY >= segmentStartY)
				&& (posY <= segmentEndY);
		if (isWithinRange) {
			log.debug("The sensor : {} is within the Range of segment :{}", sensor.getSensorId(),
					segment.getSegmentId());
		} else {
			log.debug("The sensor doesn't falls within the segmentRange");
		}
		return isWithinRange;
	}

	/**
	 * This method used to retrieve sensor data for current section in bulk
	 * manner.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionSensor
	 *            the section sensor
	 * @return the list
	 */
	public List<SensorDataDTO> retrieveSensorDataList(Long layoutId, List<SectionSensorMappingDTO> sectionSensor) {
		List<Long> sensorIdList = sectionSensor.stream().map(sensor -> sensor.getSensorId())
				.collect(Collectors.toList());
		List<SensorData> sensorDataList = sensorDataRepository.findByLayoutIdAndSensorIds(layoutId, sensorIdList);
		List<SensorDataDTO> sensorDtoList = sensorDataMapper.toDto(sensorDataList);
		return sensorDtoList;
	}

	/**
	 * This method is used to retrieve kpi for current section for current
	 * section in bulk manner.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @return the list
	 */
	public List<KPIDTO> retrieveKpiForCurrentSection(Long layoutId, Long sectionId) {
		List<KPI> kpiList = kpiRespository.findByLayoutIdAndSectionId(layoutId, sectionId);
		List<KPIDTO> kpiDTOList = kpiMapper.toDto(kpiList);

		return kpiDTOList;
	}

	public SensorDataDTO retrieveSensorData(Long layoutId, Long sensorId) {
		SensorData sensorData = sensorDataRepository.findBySensorIdAndLayoutId(layoutId, sensorId);
		SensorDataDTO sensorDto = sensorDataMapper.toDto(sensorData);
		return sensorDto;
	}

	public void getSectionBasedKpiValues(SectionDTO sectionDTO,
			List<SectionSensorMappingDTO> sectionSensorMappingDTOList, Map<ZoneType, List<KPIDTO>> zoneBasedKpiValueMap,
			SectionwithkpiResponseDTO sectionWithKpiResponseDTO) {
		Map<ZoneType, List<SectionSensorMappingDTO>> zoneBasedSectionMappings = sectionSensorMappingDTOList.stream()
				.collect(Collectors.groupingBy(p -> p.getZoneType(), Collectors.toList()));
		List<ZonewithkpisResponseDTO> zoneWithKpis = new ArrayList<>();
		zoneBasedSectionMappings.keySet().forEach(zoneType -> {
			List<KPIDTO> kpiDtoList = zoneBasedKpiValueMap.get(zoneType);

			if (ServiceUtil.isNotEmptyResult(kpiDtoList)) {
				ZonewithkpisResponseDTO zoneWithKpi = new ZonewithkpisResponseDTO();
				List<OptimalKpiValueResponseDTO> optimalKpiValueList = new ArrayList<>();
				kpiDtoList.forEach(kpi -> {
					OptimalKpiValueResponseDTO optimalKpi = new OptimalKpiValueResponseDTO();
					optimalKpi.setKpiName(kpi.getKpiName());
					optimalKpi.setOptimalValueRange(kpi.getOptimalValue());
					optimalKpiValueList.add(optimalKpi);
				});
				zoneWithKpi.setZoneType(zoneType);
				zoneWithKpi.setOptimalKpiValues(optimalKpiValueList);
				zoneWithKpis.add(zoneWithKpi);
			}
		});
		sectionWithKpiResponseDTO.setSectionName(sectionDTO.getSectionName());
		sectionWithKpiResponseDTO.setSectionDescription(sectionDTO.getSectionDesc());
		sectionWithKpiResponseDTO.setZoneWithKpis(zoneWithKpis);
		log.debug("Optimal Kpi values based on the section : {}", sectionWithKpiResponseDTO);
	}

	public void setSensorStatusResult(Long sensorId, SegmentDTO segmentDto, SensorDataDTO sensorDataDto,
			SectionSensorMappingDTO sensor, SensorWithKpi sensorWithKpiResponse) {
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
		if (sensorDataDto.getParam1() != null) {
			OptimalKpiValueResponseDTO optimalValue = new OptimalKpiValueResponseDTO();
			optimalValue.setKpiName(sensorDataDto.getParam1());
			optimalValue.setOptimalValueRange(sensorDataDto.getParamValue1());
			optimalValueList.add(optimalValue);
		}
		if (sensorDataDto.getParam2() != null) {
			OptimalKpiValueResponseDTO optimalValue1 = new OptimalKpiValueResponseDTO();
			optimalValue1.setKpiName(sensorDataDto.getParam2());
			optimalValue1.setOptimalValueRange(sensorDataDto.getParamValue2());
			optimalValueList.add(optimalValue1);
		}
		if (sensorDataDto.getParam3() != null) {
			OptimalKpiValueResponseDTO optimalValue2 = new OptimalKpiValueResponseDTO();
			optimalValue2.setKpiName(sensorDataDto.getParam3());
			optimalValue2.setOptimalValueRange(sensorDataDto.getParamValue3());
			optimalValueList.add(optimalValue2);
		}
		sensorWithKpiResponse.setKpiValues(optimalValueList);
	}

	public Map<ZoneType, List<ActualKpi>> determineCurrentConditionSegment(
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

	public void retrieveSegmentWithKpi(Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap,
			List<SectionSensorMappingDTO> sensorList, List<SensorDataDTO> currentSectionSensorData, Segment segment,
			SegmentDTO segmentDto, SegmentWithkpiResponse response) {
		// Retrieve Sensor applicable for segment
		Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap = this
				.identiySensorForCurrentSegment(currentSectionSensorMap, segmentDto, null);
		Map<ZoneType, List<ActualKpi>> ZoneKPIMap = null;
		if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
			// Retrieve latest sensorData
			Map<Long, SensorDataDTO> sensorActualValueMap = this.identifyCurrentDataFromSensors(segmentSensorMap,
					currentSectionSensorData);
			ZoneKPIMap = this.determineCurrentConditionSegment(sensorActualValueMap, sensorList);

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

	/**
	 * Fetch per section details.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionDTO
	 *            the section DTO
	 * @return the sections response DTO
	 */
	/**
	 * @param layoutId
	 * @param sectionDTO
	 * @return
	 */
	public SectionsResponseDTO fetchPerSectionDetails(Long layoutId, SectionDTO sectionDTO) {
		SectionsResponseDTO sectionResponseDTO = new SectionsResponseDTO();
		if (sectionDTO != null) {
			// Enrich section details to the response
			sectionResponseDTO = layoutVisualizationMapper.sectiontoSectionResponse(sectionDTO);
			log.debug("Enrich Section value to Response : {} ", sectionResponseDTO);
			// bulk call for sectionSensorMapping
			Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap = this
					.getAllSensorsForSection(sectionDTO);
			log.debug("SensorList : {} for specific section :{} from SectionSensorMapping", currentSectionSensorMap,
					sectionDTO.getSectionId());

			List<KPIDTO> currentSectionKpi = this.retrieveKpiForCurrentSection(layoutId, sectionDTO.getSectionId());
			log.debug("Optimal KPI Values : {}  for currentSection : {}", currentSectionKpi, sectionDTO.getSectionId());

			List<SectionSensorMappingDTO> sensorList = new ArrayList<>();
			List<SensorDataDTO> currentSectionSensorData = new ArrayList<>();
			List<SensorCoverageRangeDTO> sensorCoverageRangeList = null;
			Map<Long, List<SensorCoverageRangeDTO>> sensorCoverageRangeMap = new HashMap<>();
			if (!currentSectionSensorMap.entrySet().isEmpty()) {
				sensorList = currentSectionSensorMap.entrySet().iterator().next().getValue();
				if (ServiceUtil.isNotEmptyResult(sensorList)) {
					currentSectionSensorData = this.retrieveSensorDataList(layoutId, sensorList);
					log.debug("currentSectionSensorData : {}  for currentSection : {}", currentSectionSensorData,
							sectionDTO.getSectionId());
					List<Long> sensorIdList = sensorList.stream().map(v -> v.getSensorId())
							.collect(Collectors.toList());
					sensorCoverageRangeList = getSensorCoverageByIds(sectionDTO.getLayoutId(),
							sectionDTO.getSectionId(), sensorIdList);
					sensorCoverageRangeMap = sensorCoverageRangeList.stream()
							.collect(Collectors.groupingBy(SensorCoverageRangeDTO::getSensorId));
					log.debug("sensorCoverageRangeMap :{}", sensorCoverageRangeMap);
				}
			}
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

					OverallThresholdstateEnum thresholdState = fetchPerSegmentDetails(sectionDTO,
							currentSectionSensorMap, currentSectionKpi, currentSectionSensorData, segment,
							sensorCoverageRangeMap);
					// Enrich the overall Threshold state to segment
					// response
					segmentsResponseDTO.setOverallThresholdstate(thresholdState);
					segmentsResponseDTOList.add(segmentsResponseDTO);
				}
				// Enrich the segments list to response
				sectionResponseDTO.setSegments(segmentsResponseDTOList);
				log.debug("Enriched complete Data for section details to the Response : {}", sectionResponseDTO);
			}
		}
		return sectionResponseDTO;

	}

	private OverallThresholdstateEnum fetchPerSegmentDetails(SectionDTO sectionDTO,
			Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap, List<KPIDTO> currentSectionKpi,
			List<SensorDataDTO> currentSectionSensorData, SegmentDTO segment,
			Map<Long, List<SensorCoverageRangeDTO>> sensorCoverageRangeMap) {
		Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap = this
				.identiySensorForCurrentSegment(currentSectionSensorMap, segment, sensorCoverageRangeMap);
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = null;
		Map<Long, SensorDataDTO> sensorActualValueMap = null;
		OverallThresholdstateEnum thresholdState = OverallThresholdstateEnum.NORMAL;
		// Retrieve Optimal KPI values
		if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
			sensorOptimalKPIMap = this.identifyOptimalKPIsForSensors(sectionDTO, segmentSensorMap, currentSectionKpi);
			if (sensorOptimalKPIMap != null && !sensorOptimalKPIMap.isEmpty()) {
				// Retrieve Actual KPI Values
				sensorActualValueMap = this.identifyCurrentDataFromSensors(segmentSensorMap, currentSectionSensorData);
				// calculate overall threshold
				if (sensorActualValueMap != null && !sensorActualValueMap.isEmpty()) {
					thresholdState = this.calculateOverallThresholdState(sensorActualValueMap, sensorOptimalKPIMap);

				}
			}
		}
		return thresholdState;
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
	public List<SegmentDTO> retrieveSegment(Long layoutId, SectionDTO sectionDTO) {
		List<Segment> segmentList = segmentRepository.findByLayoutIdAndSectionId(layoutId, sectionDTO.getSectionId());
		log.debug("Segment details fetched from DB : {} ", segmentList);

		List<SegmentDTO> segmentDTOList = segmentMapper.toDto(segmentList);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDTOList, layoutId);
		return segmentDTOList;
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
	public SegmentDTO retrieveSegment(Long layoutId, Long sectionId, Long segmentId) {
		Segment segment = segmentRepository.findByLayoutIdAndSectionIdAndSegmentId(layoutId, sectionId, segmentId);
		log.debug("Segment details fetched from DB : {} ", segment);

		SegmentDTO segmentDTO = segmentMapper.toDto(segment);
		log.debug("List of Segment details : {}  mapped for layoutId : {} ", segmentDTO, layoutId);
		return segmentDTO;
	}

	public void fetchZoneStatus(SectionDTO sectionDTO,
			Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap, List<KPIDTO> currentSectionKpi,
			List<SensorDataDTO> currentSectionSensorData, SegmentZoneDetailsResponse segmentZoneDetails,
			SegmentDTO segmentDTO) {
		segmentZoneDetails.setSegmentName(segmentDTO.getSegmentName());
		segmentZoneDetails.setSegmentDescription(segmentDTO.getSegmentDesc());
		segmentZoneDetails.setSegmentX(segmentDTO.getStartX());
		segmentZoneDetails.setSegmentY(sectionDTO.getStartY());

		// Retrieve sensor applicable for segment
		Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap = this
				.identiySensorForCurrentSegment(currentSectionSensorMap, segmentDTO, null);

		// Retrieve Optimal KPI values
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = null;
		Map<Long, SensorDataDTO> sensorActualValueMap = null;
		Map<Long, OverallThresholdstateEnum> thresholdState = null;
		if (segmentSensorMap != null && !segmentSensorMap.entrySet().isEmpty()) {
			sensorOptimalKPIMap = this.identifyOptimalKPIsForSensors(sectionDTO, segmentSensorMap, currentSectionKpi);
			if (sensorOptimalKPIMap != null && !sensorOptimalKPIMap.isEmpty()) {
				// Retrieve Actual KPI Values
				sensorActualValueMap = this.identifyCurrentDataFromSensors(segmentSensorMap, currentSectionSensorData);
				if (sensorActualValueMap != null && !sensorActualValueMap.isEmpty()) {
					thresholdState = this.calculateThresholdStateOfSensors(sensorActualValueMap, sensorOptimalKPIMap);
				}

			}
			Long segmentId = segmentDTO.getSegmentId();
			// Retrieve section senor Mapping values for specific segment
			List<ZoneStatus> zoneStatusList = new ArrayList<>();
			List<SectionSensorMappingDTO> setionSensorMappingList = segmentSensorMap.get(segmentId);
			List<Long> sensorListFromSection = setionSensorMappingList.stream().map(v -> v.getSensorId())
					.collect(Collectors.toList());
			List<SensorDTO> sensorDtoList = retrieveSensorInfo(sectionDTO.getLayoutId(), sensorListFromSection);
			log.debug("sensorDtoList : {}", sensorDtoList);
			// Convert List of sensorDTO to Map : key as sensorId
			Map<Long, String> sensorDescMap = sensorDtoList.stream()
					.collect(Collectors.toMap(SensorDTO::getSensorId, SensorDTO::getSensorDesc));
			Map<Long, String> sensorNameMap = sensorDtoList.stream()
					.collect(Collectors.toMap(SensorDTO::getSensorId, SensorDTO::getSensorName));

			if (ServiceUtil.isNotEmptyResult(setionSensorMappingList)) {
				for (SectionSensorMappingDTO sectionSensor : setionSensorMappingList) {
					if (sectionSensor != null && sectionSensor.getSensorId() != null) {
						Long sensorId = sectionSensor.getSensorId();
						List<KPIDTO> kpiDtoList = sensorOptimalKPIMap.get(sensorId);
						List<ZoneType> zoneListBasedOnSensor = new ArrayList<>();
						if (ServiceUtil.isNotEmptyResult(kpiDtoList)) {
							zoneListBasedOnSensor = kpiDtoList.stream().map(kpiList -> kpiList.getZoneType())
									.collect(Collectors.toList());
							if (ServiceUtil.isNotEmptyResult(zoneListBasedOnSensor)
									&& zoneListBasedOnSensor.contains(sectionSensor.getZoneType())) {
								ZoneStatus zoneStatus = new ZoneStatus();
								zoneStatus.setZoneType(sectionSensor.getZoneType());
								zoneStatus.setPosX(sectionSensor.getPosX());
								zoneStatus.setPosY(sectionSensor.getPosY());
								zoneStatus.setSensorDesc(sensorDescMap.get(sensorId));
								zoneStatus.setSensorName(sensorNameMap.get(sensorId));
								zoneStatus.setThresholdState(thresholdState.get(sensorId));
								zoneStatus.setSensorId(sensorId);
								zoneStatusList.add(zoneStatus);
							}
						}
					}
				}
			}
			log.debug("Intermediate DTO for ZoneStatus : {}", zoneStatusList);
			Map<ZoneType, List<ZoneStatus>> sensorsGroupByZone = zoneStatusList.stream()
					.collect(Collectors.groupingBy(ZoneStatus::getZoneType));
			List<Zones> zones = new ArrayList<>();
			sensorsGroupByZone.forEach((key, value) -> {
				Zones zone = new Zones();
				zone.setKey(key);
				List<SensorsResponse> sensorResponseList = new ArrayList<>();
				value.forEach(v -> {
					SensorsResponse sensorResponse = new SensorsResponse();
					sensorResponse.setHeightY(v.getPosY());
					sensorResponse.setWidthX(v.getPosX());
					sensorResponse.setSensorDescription(v.getSensorDesc());
					sensorResponse.setSensorName(v.getSensorName());
					sensorResponse.setSensorId(v.getSensorId());
					sensorResponse.setThresholdState(v.getThresholdState());
					sensorResponseList.add(sensorResponse);
				});
				zone.setSensors(sensorResponseList);
				zones.add(zone);
			});
			segmentZoneDetails.setZones(zones);
		}
	}

	public void findHistoricalKpiValues(Long segmentId, Map<Long, List<SectionSensorMappingDTO>> segmentSensorMap,
			Segmentkpichart segmentKpiChartValues) {
		// Retrieve latest sensorData
		List<SectionSensorMappingDTO> sectionSensorMappingDtoList = segmentSensorMap.get(segmentId);
		sectionSensorMappingDtoList.forEach(sectionSensorMappingDto -> {

			List<KPI> kpiList = kpiRespository.findByLayoutIdAndSectionIdAndZoneType(
					sectionSensorMappingDto.getLayoutId(), sectionSensorMappingDto.getSectionId(),
					sectionSensorMappingDto.getZoneType());
			log.debug("KpiList :{} for the layoutId:{} sectionId:{} and ZoneType: {}", kpiList,
					sectionSensorMappingDto.getLayoutId(), sectionSensorMappingDto.getSectionId(),
					sectionSensorMappingDto.getZoneType());
			segmentKpiChartValues.setZoneType(sectionSensorMappingDto.getZoneType());
			List<KPIDTO> kpiDtoList = kpiMapper.toDto(kpiList);
			List<KpiValuesForTheCurrentSegment> kpiValuesList = new ArrayList<>();
			kpiDtoList.forEach(kpi -> {
				KpiValuesForTheCurrentSegment kpiValues = new KpiValuesForTheCurrentSegment();
				kpiValues.setKpiName(kpi.getKpiName());
				kpiValues.setLowerRange(kpi.getLowerRefLimit());
				kpiValues.setUpperRange(kpi.getUpperRefLimit());
				// need to check with veera average
				kpiValues.setAverage(kpi.getOptimalValue());
				kpiValuesList.add(kpiValues);

			});
			segmentKpiChartValues.setKpiValues(kpiValuesList);
		});
	}

	public List<SensorDTO> retrieveSensorInfo(Long layoutId, List<Long> sensorIdList) {
		List<Sensor> sensorList = sensorRepository.findByLayoutIdAndSensorId(layoutId, sensorIdList);
		List<SensorDTO> sensorDtoList = sensorMapper.toDto(sensorList);
		return sensorDtoList;
	}
}
