package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.Sensor;
import com.eagro.service.dto.SensorDTO;

/**
 * Mapper for the entity Sensor and its DTO SensorDTO.
 */
@Mapper(componentModel = "spring", uses = {SensorCoverageRangeMapper.class, SectionSensorMappingMapper.class, SensorDataMapper.class, LayoutMapper.class})
public interface SensorMapper extends EntityMapper<SensorDTO, Sensor> {

    @Mapping(source = "layout.layoutId", target = "layoutId")
    SensorDTO toDto(Sensor sensor);

    @Mapping(source = "layoutId", target = "layout")
    Sensor toEntity(SensorDTO sensorDTO);

    default Sensor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sensor sensor = new Sensor();
        sensor.setId(id);
        return sensor;
    }
}
