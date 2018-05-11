package com.eagro.service.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentsResponseDTO.OverallThresholdstateEnum;
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.impl.LayoutVisualizationServiceImpl;
import com.eagro.service.mapper.KPIMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
import com.eagro.service.mapper.SensorCoverageRangeMapper;
import com.eagro.service.mapper.SensorDataMapper;
import com.eagro.service.utils.ServiceUtil;

@Component
@Transactional
public class SegmentDetailsService {

	private static final String LIGHT = "Light";

	private final Logger log = LoggerFactory.getLogger(LayoutVisualizationServiceImpl.class);

	@Autowired
	public LayoutVisualizationMapper layoutVisualizationMapper;

	@Autowired
	public SectionSensorMappingRepository sectionSensorMappingRepository;
	@Autowired
	public SectionSensorMappingMapper sectionSensorMappingMapper;
	@Autowired
	public SensorCoverageRangeRepository sensorCoverageRangeRepository;

	@Autowired
	public KPIRepository kpiRespository;

	@Autowired
	public SensorDataRepository sensorDataRepository;

	@Autowired
	public SensorDataMapper sensorDataMapper;
	@Autowired
	public KPIMapper kpiMapper;

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
							// TODO Logic to check within reference range and
							// deviation range
							Double interimVar = sensor.getParamValue1();
							if (kpi.getLowerRefLimit()<= interimVar && kpi.getUpperRefLimit() >= interimVar) {
								if((kpi.getLowerRefLimit() + kpi.getDeviationRange() <= interimVar) && ((kpi.getUpperRefLimit()- kpi.getDeviationRange()) >= interimVar)) {
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
						&& (currentState.equals(OverallThresholdstateEnum.NORMAL))) {
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
	 * @return the map
	 */
	public Map<Long, SensorDataDTO> retrieveCurrentDataFromSensors(
			Map<Long, SectionSensorMappingDTO> segmentSensorMap) {
		Map<Long, SensorDataDTO> sensorActualValueMap = new HashMap<>();
		// Iterate Map with key
		if (segmentSensorMap.values() != null && !segmentSensorMap.values().isEmpty()) {
			segmentSensorMap.values().forEach(sensor -> {
				// Retrieve latest recorded sensor data from sensorData entity
				SensorData sensorData = sensorDataRepository.findBySensorIdAndLayoutId(sensor.getLayoutId(),
						sensor.getSensorId());
				SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);
				if (sensorDataDTO != null && sensorDataDTO.getSensorId() != null) {
				sensorActualValueMap.put(sensorDataDTO.getSensorId(), sensorDataDTO);
				}
			});
		}
		log.trace("The Actual KPI values from Sensor Latest Data : {} ", sensorActualValueMap.values());
		return sensorActualValueMap;
	}

	public Map<Long, List<KPIDTO>> retrieveOptimalKPIsForSensors(SectionDTO section,
			Map<Long, SectionSensorMappingDTO> segmentSensorMap) {
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = new HashMap<>();
		segmentSensorMap.values().forEach(senor -> {
			
			// Retrieve zoneType from sectionsensormapping entity
			SectionSensorMapping sensor  = sectionSensorMappingRepository.findByLayoutIdAndSectionIdAndSensorId(
					section.getLayoutId(), section.getSectionId(), senor.getSensorId());
			SectionSensorMappingDTO sensorDTO = sectionSensorMappingMapper.toDto(sensor);

			// Retrieve optimal KPI vlaues form KPI entity
			if(sensorDTO != null && sensorDTO.getZoneType() != null) {
			List<KPIDTO> kpiDTOList = retrieveKpi(section.getLayoutId(),
					section.getSectionId(), sensorDTO.getZoneType());
			
			sensorOptimalKPIMap.put(sensorDTO.getSensorId(), kpiDTOList);
			}

		});
		return sensorOptimalKPIMap;
	}

	public List<KPIDTO> retrieveKpi(Long layoutId, Long sectionId, ZoneType zoneType) {
		
		List<KPI> kpiList = kpiRespository.findByLayoutIdAndSectionIdAndZoneType(layoutId,
						sectionId, zoneType);
		List<KPIDTO> kpiDTOList = kpiMapper.toDto(kpiList);
		return kpiDTOList;
	}
	/**
	 * Retrieve list of sensors applicable for current segment.
	 *
	 * @param sectionDTO
	 *            the section DTO
	 * @param segmentDTO
	 *            the segment DTO
	 * @return the map
	 */
	public Map<Long, SectionSensorMappingDTO> retrieveSensorForCurrentSegment(SectionDTO sectionDTO,
			SegmentDTO segmentDTO) {

		Map<Long, SectionSensorMappingDTO> segmentSensorMap = new HashMap<>();
		// Retrieve all sensor for the section from sectionsensorMapping entity
		// using layoutId and sectionId
		List<SectionSensorMapping> sensorList = sectionSensorMappingRepository
				.findBySectionIdAndLayoutId(sectionDTO.getLayoutId(), sectionDTO.getSectionId());
		log.debug("sensorList Data Fetched from sectionSensorMappingRepository DB : {} for layoutId : {}", sensorList, sectionDTO.getLayoutId());
		
		// Mapper entityToDTO
		List<SectionSensorMappingDTO> sensorDTOList = sectionSensorMappingMapper.toDto(sensorList);
		
		log.debug("sensorList Data : {} for layoutId : {}", sensorDTOList, sectionDTO.getLayoutId());
		// validate whether the coordinates of the sensor falls within the
		// segment coordinates
		for (SectionSensorMappingDTO sensor : sensorDTOList) {
			SectionSensorMappingDTO interimSectionBasedSensor = null;
			if (checkSensorWithInSegRange(segmentDTO, sensor)) {
				interimSectionBasedSensor = layoutVisualizationMapper.sensorToSensor(sensor);
				log.debug("The interim sensor :{} is within segment Range and added to segmentSensorMap with segmentId: {}", interimSectionBasedSensor, segmentDTO.getSegmentId());
				segmentSensorMap.put(segmentDTO.getSegmentId(), interimSectionBasedSensor);

			}
		}
		log.debug("segmentSensorMap : {}", segmentSensorMap);
		// Logic to consider Water sensorCoverageRange
		// Retrieve all sensor for the section from sectionsensorMapping entity
		// using layoutId,sectionId and ZoneType
		List<SectionSensorMapping> waterSensorList  = sectionSensorMappingRepository
				.findByLayoutIdAndSectionIdAndZoneType(sectionDTO.getLayoutId(), sectionDTO.getSectionId(),
						ZoneType.WATER);
		// Mapper entityToDTO
		List<SectionSensorMappingDTO> waterSensorDTOList = sectionSensorMappingMapper.toDto(waterSensorList);
		// Check waterSensor is available in Sensor Interim Object
		// TODO Need to check with Veera
		log.debug("Water Sensor List : {}", waterSensorDTOList);
		segmentSensorMap.values().forEach(sectionSensorMapping -> {
			if (!waterSensorDTOList.contains(sectionSensorMapping)) {
				// Retrieve sensorCoveragerange from entity based on
				// layout,section and sensorId
				log.debug("Considering Water Coverage Range");
				SensorCoverageRange currentSensorCoverageRange  = sensorCoverageRangeRepository
						.findByLayoutIdAndSectionIdAndSensorId(sectionSensorMapping.getLayoutId(),
								sectionSensorMapping.getSectionId(), sectionSensorMapping.getSensorId());
				log.debug("Retrieve current SensorRange");
				SensorCoverageRangeDTO sensorCoverageRange = sensorCoverageRangeMapper
						.toDto(currentSensorCoverageRange);
				log.debug("sensorCoverageRange : {} and segmentDTO : {}", sensorCoverageRange, segmentDTO );
				if (checkSensorCoverageWithinsegRange(segmentDTO, sensorCoverageRange)) {
					segmentSensorMap.put(segmentDTO.getSegmentId(), sectionSensorMapping);
				}
			}
		});
		log.debug("The segment sensor Map : {}  contains sensor coverage range is within segment range");
		return segmentSensorMap;
	}

	/**
	 * Check whether the sensor coverage range is with in the segment
	 * coordinates range.
	 *
	 * @param segmentDTO
	 *            the segment DTO
	 * @param currentSensorCoverageRangeDTO
	 *            the current sensor coverage range DTO
	 * @return true, if successful
	 */
	private boolean checkSensorCoverageWithinsegRange(SegmentDTO segmentDTO,
			SensorCoverageRangeDTO currentSensorCoverageRangeDTO) {
		return (currentSensorCoverageRangeDTO.getStartX() <= segmentDTO.getEndX())
				&& (currentSensorCoverageRangeDTO.getEndX() >= currentSensorCoverageRangeDTO.getStartX())
				&& (currentSensorCoverageRangeDTO.getStartY() <= segmentDTO.getEndY())
				&& (currentSensorCoverageRangeDTO.getEndY() >= currentSensorCoverageRangeDTO.getStartY());
	}

	/**
	 * Check sensor the corordinates Posx and Posy falls with in segment
	 * coordinates x and y range.
	 *
	 * @param segment
	 *            the segment
	 * @param sensor
	 *            the sensor
	 * @return true, if successful
	 */
	private boolean checkSensorWithInSegRange(SegmentDTO segment, SectionSensorMappingDTO sensor) {
		return (sensor.getPosX() >= segment.getStartX()) && (sensor.getPosX() <= segment.getEndX())
				&& (sensor.getPosX() >= segment.getStartX()) && (sensor.getPosX() <= segment.getEndX());
	}

}
