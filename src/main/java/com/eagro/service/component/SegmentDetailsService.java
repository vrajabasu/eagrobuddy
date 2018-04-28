package com.eagro.service.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.eagro.service.dto.SensorCoverageRangeDTO;
import com.eagro.service.dto.SensorDataDTO;
import com.eagro.service.dto.enumeration.ThresholdState;
import com.eagro.service.impl.LayoutVisualizationServiceImpl;
import com.eagro.service.mapper.KPIMapper;
import com.eagro.service.mapper.LayoutVisualizationMapper;
import com.eagro.service.mapper.SectionSensorMappingMapper;
import com.eagro.service.mapper.SensorCoverageRangeMapper;
import com.eagro.service.mapper.SensorDataMapper;

@Component
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

	public ThresholdState calculateOverallThresholdState(Map<Long, SensorDataDTO> sensorActualValueMap,
			Map<Long, List<KPIDTO>> sensorOptimalKPIMap) {
		// TODO Auto-generated method stub
		List<ThresholdState> currentThresholdStateList = new ArrayList<>();
		// Iterate sensorActualValue Map
		sensorActualValueMap.values().forEach(sensor -> {
			ThresholdState currentSensorState = ThresholdState.NORMAL;
			// Iterate Kpi
			if (!(LIGHT.equalsIgnoreCase(sensor.getParam1())) && !(LIGHT.equalsIgnoreCase(sensor.getParam2()))
					&& !(LIGHT.equalsIgnoreCase(sensor.getParam3()))) {
				sensorOptimalKPIMap.values().forEach(kpiList -> {
					kpiList.forEach(kpi -> {

					});
				});
			}

		});
		return null;
	}

	private ThresholdState calculateThresholdBasedOnPrecendence(List<ThresholdState> currentThresholdStateList) {
		ThresholdState thresholdState = ThresholdState.NORMAL;
		for (ThresholdState currentState : currentThresholdStateList) {
			if (!currentState.equals(ThresholdState.NORMAL)) {
				if (currentState.equals(ThresholdState.EXCEEDING_SOON)
						&& (currentState.equals(ThresholdState.NORMAL))) {
					thresholdState = ThresholdState.EXCEEDING_SOON;
				}
				if (currentState.equals(ThresholdState.EXCEEDED)) {
					thresholdState = ThresholdState.EXCEEDED;
				}
			}
		}

		return thresholdState;

	}

	public Map<Long, SensorDataDTO> retrieveCurrentDataFromSensors(
			Map<Long, SectionSensorMappingDTO> segmentSensorMap) {
		// TODO Auto-generated method stub
		Map<Long, SensorDataDTO> sensorActualValueMap = new HashMap<>();
		// Iterate Map with key
		if (segmentSensorMap.values() != null && !segmentSensorMap.values().isEmpty()) {
			segmentSensorMap.values().forEach(sensor -> {
				// Retrieve latest recorded sensor data from sensorData entity
				SensorData sensorData = sensorDataRepository.findBySensorIdAndLayoutId(sensor.getLayoutId(),
						sensor.getSensorId());
				SensorDataDTO sensorDataDTO = sensorDataMapper.toDto(sensorData);
				sensorActualValueMap.put(sensorDataDTO.getSensorId(), sensorDataDTO);

			});
		}
		log.trace("The Actual KPI values from Sensor Latest Data : {} ", sensorActualValueMap.values());
		return sensorActualValueMap;
	}

	public Map<Long, List<KPIDTO>> retrieveOptimalKPIsForSensors(SectionDTO section,
			Map<Long, SectionSensorMappingDTO> segmentSensorMap) {
		// TODO Auto-generated method stub
		Map<Long, List<KPIDTO>> sensorOptimalKPIMap = new HashMap<>();
		segmentSensorMap.values().forEach(senor -> {
			// Retrieve zoneType from sectionsensormapping entity
			SectionSensorMapping sensor = sectionSensorMappingRepository.findByLayoutIdAndSectionIdAndSensorId(
					section.getLayoutId(), section.getSectionId(), senor.getSensorId());
			SectionSensorMappingDTO sensorDTO = sectionSensorMappingMapper.toDto(sensor);

			ZoneType zoneType = sensorDTO.getZoneType();
			// Retrieve optimal KPI vlaues form KPI entity
			List<KPI> kpiList = kpiRespository.findByLayoutIdAndSectionIdAndZoneType(section.getLayoutId(),
					section.getSectionId(), zoneType);
			List<KPIDTO> kpiDTOList = kpiMapper.toDto(kpiList);
			sensorOptimalKPIMap.put(sensorDTO.getSensorId(), kpiDTOList);

		});
		return sensorOptimalKPIMap;
	}

	public Map<Long, SectionSensorMappingDTO> retrieveSensorForCurrentSegment(SectionDTO sectionDTO,
			SegmentDTO segmentDTO) {

		Map<Long, SectionSensorMappingDTO> segmentSensorMap = new HashMap<>();
		// Retrieve all sensor for the section from sectionsensorMapping entity
		// using layoutId and sectionId
		List<SectionSensorMapping> sensorList = sectionSensorMappingRepository
				.findBySectionIdAndLayoutId(sectionDTO.getLayoutId(), sectionDTO.getSectionId());
		// Mapper entityToDTO
		List<SectionSensorMappingDTO> sensorDTOList = sectionSensorMappingMapper.toDto(sensorList);
		// validate whether the coordinates of the sensor falls within the
		// segment coordinates
		// List<SectionSensorMappingDTO> sectionBasedSensorList = new
		// ArrayList<SectionSensorMappingDTO>();
		for (SectionSensorMappingDTO sensor : sensorDTOList) {
			SectionSensorMappingDTO interimSectionBasedSensor = null;
			if (checkSensorWithInSegRange(segmentDTO, sensor)) {
				interimSectionBasedSensor = layoutVisualizationMapper.sensorToSensor(sensor);
				// sectionBasedSensorList.add(interimSectionBasedSensor);
				segmentSensorMap.put(segmentDTO.getSegmentId(), interimSectionBasedSensor);

			}
		}
		// Logic to consider Water sensorCoverageRange
		// Retrieve all sensor for the section from sectionsensorMapping entity
		// using layoutId,sectionId and ZoneType
		List<SectionSensorMapping> waterSensorList = sectionSensorMappingRepository
				.findByLayoutIdAndSectionIdAndZoneType(sectionDTO.getLayoutId(), sectionDTO.getSectionId(),
						ZoneType.WATER);
		// Mapper entityToDTO
		List<SectionSensorMappingDTO> waterSensorDTOList = sectionSensorMappingMapper.toDto(waterSensorList);
		// Check waterSensor is available in Sensor Interim Object
		// TODO Need to check with Veera
		segmentSensorMap.values().forEach(sectionSensorMapping -> {
			if (!waterSensorDTOList.contains(sectionSensorMapping)) {
				// Retrieve sensorCoveragerange from entity based on
				// layout,section and sensorId
				SensorCoverageRange currentSensorCoverageRange = sensorCoverageRangeRepository
						.findByLayoutIdAndSectionIdAndSensorId(sectionSensorMapping.getLayoutId(),
								sectionSensorMapping.getSectionId(), sectionSensorMapping.getSensorId());
				SensorCoverageRangeDTO sensorCoverageRange = sensorCoverageRangeMapper
						.toDto(currentSensorCoverageRange);
				if (checkSensorCoverageWithinsegRange(segmentDTO, sensorCoverageRange)) {
					segmentSensorMap.put(segmentDTO.getSegmentId(), sectionSensorMapping);
				}
			}
		});

		return segmentSensorMap;
	}

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
