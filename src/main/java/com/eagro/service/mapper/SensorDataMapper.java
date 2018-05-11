package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.SensorData;
import com.eagro.service.dto.SensorDataDTO;

/**
 * Mapper for the entity SensorData and its DTO SensorDataDTO.
 */
@Mapper(componentModel = "spring", uses = {LayoutMapper.class})
public interface SensorDataMapper extends EntityMapper<SensorDataDTO, SensorData> {

    @Mapping(source = "layout.layoutId", target = "layoutId")
    @Mapping(source = "sensor_id", target = "sensorId")
    SensorDataDTO toDto(SensorData sensorData);

    @Mapping(source = "layoutId", target = "layout.layoutId")
    @Mapping(source = "sensorId", target = "sensor_id")
    SensorData toEntity(SensorDataDTO sensorDataDTO);

    default SensorData fromId(Long id) {
        if (id == null) {
            return null;
        }
        SensorData sensorData = new SensorData();
        sensorData.setId(id);
        return sensorData;
    }
}
