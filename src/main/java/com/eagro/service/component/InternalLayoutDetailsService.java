package com.eagro.service.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eagro.entities.KPI;
import com.eagro.entities.SectionSensorMapping;
import com.eagro.entities.SensorCoverageRange;
import com.eagro.entities.SensorData;
import com.eagro.entities.enumeration.ZoneType;
import com.eagro.repository.KPIRepository;
import com.eagro.repository.SectionSensorMappingRepository;
import com.eagro.repository.SensorCoverageRangeRepository;
import com.eagro.repository.SensorDataRepository;
import com.eagro.service.dto.KPIDTO;
import com.eagro.service.dto.OptimalKpiValueResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentsResponseDTO.OverallThresholdstateEnum;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.ZonewithkpisResponseDTO;
import com.eagro.service.impl.LayoutVisualizationServiceImpl;
import com.eagro.service.mapper.KPIMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
import com.eagro.service.mapper.SensorCoverageRangeMapper;
import com.eagro.service.mapper.SensorDataMapper;
import com.eagro.service.utils.ServiceUtil;

/**
 * The Class SegmentDetailsService.
 */
@Component
@Transactional
public class InternalLayoutDetailsService {

	/** The Constant LIGHT. */
	private static final String LIGHT = "Light";

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
				currentThresholdStateList.add(currentSensorState);
			}
			log.trace("The sensorId : {} hold the threshold state as : {}", sensor.getSensorId(), currentSensorState);

		});

		return calculateThresholdBasedOnPrecendence(currentThresholdStateList);
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
	 * 			the current section sensor Data from bulk call
	 * @return the map
	 */
	public Map<Long, SensorDataDTO> identifyCurrentDataFromSensors(
			Map<Long, SectionSensorMappingDTO> segmentSensorMap, List<SensorDataDTO> currentSectionSensorData) {
		Map<Long, SensorDataDTO> sensorActualValueMap = new HashMap<>();
		// Iterate Map with key
		if (segmentSensorMap.values() != null && !segmentSensorMap.values().isEmpty()) {
			
			segmentSensorMap.values().forEach(sensor -> {
				log.debug("sensor Data from map : {}", sensor);
				// Retrieve latest recorded sensor data from sensorData entity
				currentSectionSensorData.forEach(sensorData -> {
					log.debug("sensor Data from currentSectionSensorData : {}", sensor);
					if(sensorData.getSensorId() != null
								&& sensorData.getSensorId().equals(sensor.getSensorId())) {
						sensorActualValueMap.put(sensor.getSensorId(), sensorData);
					}
				});
			});
		}
		log.debug("The Actual KPI values from Sensor Latest Data : {} ", sensorActualValueMap.values());
		return sensorActualValueMap;
	}

	/**
	 * Identify optimal KPI is for sensors for the current section.
	 *
	 * @param section the section
	 * @param segmentSensorMap the segment sensor map
	 * @param currentSectionKpi the current section kpi
	 * @return the map
	 */
	public Map<Long, List<KPIDTO>> identifyOptimalKPIsForSensors(SectionDTO section,
			Map<Long, SectionSensorMappingDTO> segmentSensorMap, List<KPIDTO> currentSectionKpi) {
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = new HashMap<>();
		segmentSensorMap.values().forEach(sensor -> {
			//Retrieve zoneType from segmentSensorMap
			if (sensor != null) {
					//Retrieve optimal value from currentSectionKpi
					List<KPIDTO> kpiList = new ArrayList<>();
					currentSectionKpi.forEach(kpi -> {
						if (kpi.getSectionId() == sensor.getSectionId() && sensor.getLayoutId() == section.getLayoutId() && kpi.getZoneType().equals(sensor.getZoneType())) {
							kpiList.add(kpi);
						}
					});
					sensorOptimalKPIMap.put(sensor.getSensorId(), kpiList);
			}		
		});
		log.debug("Optimal value Map  : {} with sensorId key", sensorOptimalKPIMap);
		return sensorOptimalKPIMap;
	}

	/**
	 * This method used to Retrieve kpi details for current layout, section and zonetype.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
	 * @param zoneType the zone type
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
	 * @return the map
	 */
	public Map<Long, SectionSensorMappingDTO> identiySensorForCurrentSegment(Map<SectionDTO, List<SectionSensorMappingDTO>> currentSectionSensorMap,
			SegmentDTO segmentDTO) {

		Map<Long, SectionSensorMappingDTO> segmentSensorMap = new HashMap<>();
		// validate whether the coordinates of the sensor falls within the
		// segment coordinates
		
		if (!currentSectionSensorMap.entrySet().isEmpty() && segmentDTO != null) {
			Entry<SectionDTO, List<SectionSensorMappingDTO>> singleEntryMap = currentSectionSensorMap.entrySet().iterator().next();
			SectionDTO sectionDTO = singleEntryMap.getKey();
			singleEntryMap.getValue().forEach(sensor -> {
				SectionSensorMappingDTO interimSectionBasedSensor = null;
				if (checkSensorWithInSegRange(segmentDTO, sensor, sectionDTO)) {
					interimSectionBasedSensor = layoutVisualizationMapper.sensorToSensor(sensor);
					log.debug(
							"The interim sensor :{} is within segment Range and added to segmentSensorMap with segmentId: {}",
							interimSectionBasedSensor, segmentDTO.getSegmentId());
					segmentSensorMap.put(segmentDTO.getSegmentId(), interimSectionBasedSensor);

			}
			});
			log.debug("segmentSensorMap : {}", segmentSensorMap);
			// Logic to consider Water sensorCoverageRange
			// Retrieve all sensor for the section from sectionsensorMapping
			// entity
			// using layoutId,sectionId and ZoneType
			List<SectionSensorMapping> waterSensorList = sectionSensorMappingRepository
					.findByLayoutIdAndSectionIdAndZoneType(sectionDTO.getLayoutId(), sectionDTO.getSectionId(),
							ZoneType.WATER);
			// Mapper entityToDTO
			List<SectionSensorMappingDTO> waterSensorDTOList = sectionSensorMappingMapper.toDto(waterSensorList);
			// Check waterSensor is available in Sensor Interim Object
			log.debug("Water Sensor List : {}", waterSensorDTOList);
			segmentSensorMap.values().forEach(sectionSensorMapping -> {
				if (!waterSensorDTOList.contains(sectionSensorMapping)) {
					// Retrieve sensorCoveragerange from entity based on
					// layout,section and sensorId
					log.debug("Considering Water Coverage Range");
					SensorCoverageRangeDTO sensorCoverageRange = getSensorCoverageByIds(sectionSensorMapping.getLayoutId(),
							sectionSensorMapping.getSectionId(), sectionSensorMapping.getSensorId());
					log.debug("sensorCoverageRange : {} and segmentDTO : {}", sensorCoverageRange, segmentDTO);
					
					if (sensorCoverageRange != null && checkSensorCoverageWithinsegRange(segmentDTO, sensorCoverageRange, sectionDTO)) {
						segmentSensorMap.put(segmentDTO.getSegmentId(), sectionSensorMapping);
					}
				}
			});
		}
		log.debug("The segment sensor Map : {}  contains sensor coverage range is within segment range", segmentSensorMap);
		return segmentSensorMap;
	}

	public SensorCoverageRangeDTO getSensorCoverageByIds(Long layoutId, Long sectionId, Long sensorId) {
		SensorCoverageRange currentSensorCoverageRange = sensorCoverageRangeRepository
				.findByLayoutIdAndSectionIdAndSensorId(layoutId, sectionId, sensorId);
		log.debug("Retrieve current SensorRange");
		SensorCoverageRangeDTO sensorCoverageRange = sensorCoverageRangeMapper
				.toDto(currentSensorCoverageRange);
		return sensorCoverageRange;
	}
	
	public SectionSensorMappingDTO getSensorByIds(Long layoutId, Long sectionId, Long sensorId) {
		SectionSensorMapping sensor = sectionSensorMappingRepository.findByLayoutIdAndSectionIdAndSensorId(layoutId, sectionId, sensorId);
		SectionSensorMappingDTO sectionSensor = sectionSensorMappingMapper.toDto(sensor);
		return sectionSensor;
	}
	

	/**
	 * This method used to Gets the all sensors for current section in bulk manner.
	 *
	 * @param sectionDTO the section DTO
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
	 * 		the section DTO
	 * @return true, if successful
	 */
	private boolean checkSensorCoverageWithinsegRange(SegmentDTO segmentDTO,
			SensorCoverageRangeDTO currentSensorCoverageRangeDTO, SectionDTO sectionDTO) {
		boolean isOverlapping = false;
		Double sensorStartX = currentSensorCoverageRangeDTO.getStartX() + sectionDTO.getStartX();
		Double sensorStartY = currentSensorCoverageRangeDTO.getStartY() + sectionDTO.getStartY();
		Double sensorEndX = currentSensorCoverageRangeDTO.getEndX() + sectionDTO.getEndX();
		Double sensorEndY = currentSensorCoverageRangeDTO.getEndY() + sectionDTO.getEndY();
		log.debug("Absolute value for sensorCoverage startX :{} endX : {} startY: {} endY:{} ", sensorStartX, sensorEndX, sensorStartY, sensorEndY);
		log.debug("Segment EndX : {} and EndY : {}",segmentDTO.getEndX(), segmentDTO.getEndY());
		isOverlapping = (sensorStartX <= segmentDTO.getEndX())
				&& (sensorEndX >= sensorStartX)
				&& (sensorStartY <= segmentDTO.getEndY())
				&& (sensorEndY >= sensorStartY);
		
		return isOverlapping;
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
	 * 			 the section DTO
	 * @return true, if successful
	 */
	private boolean checkSensorWithInSegRange(SegmentDTO segment, SectionSensorMappingDTO sensor, SectionDTO sectionDTO) {
		boolean isWithinRange = false;
		Double posX = sectionDTO.getStartX() + sensor.getPosX();
		Double posY = sectionDTO.getStartY() + sensor.getPosY();
		log.debug("Absolute Value for sensor PosX : {} and PosY : {}", posX, posY);
		log.debug("Segment startX : {} endX : {} and startY : {} endY : {}",segment.getStartX() , segment.getEndX(), segment.getStartY(), segment.getEndY());
		isWithinRange = (posX >= segment.getStartX()) && (posX <= segment.getEndX())
				&& (posY >= segment.getStartY()) && (posY <= segment.getEndY());
		if (isWithinRange) {
		log.debug("The sensor : {} is within the Range of segment :{}",sensor.getSensorId(), segment.getSegmentId() );
		} else
		{
			log.debug("The sensor doesn't falls within the segmentRange");
		}
		return isWithinRange;
	}

	/**
	 * This method used to retrieve sensor data for current section in bulk manner.
	 *
	 * @param layoutId the layout id
	 * @param sectionSensor the section sensor
	 * @return the list
	 */
	public List<SensorDataDTO> retrieveSensorDataList(Long layoutId, List<SectionSensorMappingDTO> sectionSensor) {
		List<Long> sensorIdList = sectionSensor.stream().map(sensor -> sensor.getSensorId()).collect(Collectors.toList());
		List<SensorData> sensorDataList = sensorDataRepository.findByLayoutIdAndSensorIds(layoutId,
				sensorIdList);
		List<SensorDataDTO> sensorDtoList = sensorDataMapper.toDto(sensorDataList);
		return sensorDtoList;
	}

	/**
	 * This method is used to retrieve kpi for current section for current section in bulk manner.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
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

}
