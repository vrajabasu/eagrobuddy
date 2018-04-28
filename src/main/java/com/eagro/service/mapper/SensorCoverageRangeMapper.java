package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.SensorCoverageRange;
import com.eagro.service.dto.SensorCoverageRangeDTO;

/**
 * Mapper for the entity SensorCoverageRange and its DTO SensorCoverageRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {LayoutMapper.class, SectionMapper.class})
public interface SensorCoverageRangeMapper extends EntityMapper<SensorCoverageRangeDTO, SensorCoverageRange> {

    @Mapping(source = "layout.layoutId", target = "layoutLayoutId")
    @Mapping(source = "section.sectionId", target = "sectionSectionId")
    SensorCoverageRangeDTO toDto(SensorCoverageRange sensorCoverageRange);

    @Mapping(source = "layoutId", target = "layout")
    @Mapping(source = "sectionId", target = "section")
    @Mapping(target = "sensors", ignore = true)
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
