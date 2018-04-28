package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.Layout;
import com.eagro.service.dto.LayoutDTO;

/**
 * Mapper for the entity Layout and its DTO LayoutDTO.
 */
@Mapper(componentModel = "spring", imports  = {UserLayoutMappingMapper.class})
public interface LayoutMapper extends EntityMapper<LayoutDTO, Layout> {


    @Mapping(target = "sections", ignore = true)
    @Mapping(target = "KPIS", ignore = true)
    @Mapping(target = "sectionSensorMappings", ignore = true)
    @Mapping(target = "sensorCoverageRanges", ignore = true)
    @Mapping(target = "segments", ignore = true)
    @Mapping(target = "sensors", ignore = true)
    @Mapping(target = "sensorData", ignore = true)
    Layout toEntity(LayoutDTO layoutDTO);

    default Layout fromId(Long id) {
        if (id == null) {
            return null;
        }
        Layout layout = new Layout();
        layout.setLayoutId(id);
        return layout;
    }
}
