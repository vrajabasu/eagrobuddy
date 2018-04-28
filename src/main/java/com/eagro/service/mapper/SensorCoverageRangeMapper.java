package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.SensorCoverageRange;
import com.eagro.service.dto.SensorCoverageRangeDTO;

/**
 * Mapper for the entity SensorCoverageRange and its DTO SensorCoverageRangeDTO.
 */
@Mapper(componentModel = "spring", uses = { LayoutMapper.class, SectionMapper.class })
public interface SensorCoverageRangeMapper extends EntityMapper<SensorCoverageRangeDTO, SensorCoverageRange> {

	@Mapping(source = "layout.layoutId", target = "layoutId")
	@Mapping(source = "section.sectionId", target = "sectionId")
	@Mapping(source = "sensor.sensorId", target = "sensorId")
	SensorCoverageRangeDTO toDto(SensorCoverageRange sensorCoverageRange);

	@Mapping(source = "layoutId", target = "layout.layoutId")
	@Mapping(source = "sectionId", target = "section.sectionId")
	@Mapping(source = "sensorId", target = "sensor.sensorId")
	SensorCoverageRange toEntity(SensorCoverageRangeDTO sensorCoverageRangeDTO);

	default SensorCoverageRange fromId(Long id) {
		if (id == null) {
			return null;
		}
		SensorCoverageRange sensorCoverageRange = new SensorCoverageRange();
		sensorCoverageRange.setId(id);
		return sensorCoverageRange;
	}
}
