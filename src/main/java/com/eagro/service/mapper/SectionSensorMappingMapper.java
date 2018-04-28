package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.SectionSensorMapping;
import com.eagro.service.dto.SectionSensorMappingDTO;

/**
 * Mapper for the entity SectionSensorMapping and its DTO
 * SectionSensorMappingDTO.
 */
@Mapper(componentModel = "spring", uses = { LayoutMapper.class, SectionMapper.class })
public abstract class SectionSensorMappingMapper
		implements EntityMapper<SectionSensorMappingDTO, SectionSensorMapping> {

	@Mapping(source = "layout.layoutId", target = "layoutId")
	@Mapping(source = "section.sectionId", target = "sectionId")
	@Mapping(source = "sensor.sensorId", target = "sensorId")
	public abstract SectionSensorMappingDTO toDto(SectionSensorMapping sectionSensorMapping);

	@Mapping(source = "layoutId", target = "layout")
	@Mapping(source = "sectionId", target = "section")
	@Mapping(source = "sensorId", target = "sensor.sensorId")
	public abstract SectionSensorMapping toEntity(SectionSensorMappingDTO sectionSensorMappingDTO);

	public SectionSensorMapping fromId(Long id) {
		if (id == null) {
			return null;
		}
		SectionSensorMapping sectionSensorMapping = new SectionSensorMapping();
		sectionSensorMapping.setId(id);
		return sectionSensorMapping;
	}

}
