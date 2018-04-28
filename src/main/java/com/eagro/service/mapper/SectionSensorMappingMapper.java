package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.SectionSensorMapping;
import com.eagro.service.dto.SectionSensorMappingDTO;

/**
 * Mapper for the entity SectionSensorMapping and its DTO SectionSensorMappingDTO.
 */
@Mapper(componentModel = "spring", uses = {LayoutMapper.class, SectionMapper.class})
public interface SectionSensorMappingMapper extends EntityMapper<SectionSensorMappingDTO, SectionSensorMapping> {

    @Mapping(source = "layout.layoutId", target = "layoutLayoutId")
    @Mapping(source = "section.sectionId", target = "sectionSectionId")
    SectionSensorMappingDTO toDto(SectionSensorMapping sectionSensorMapping);

    @Mapping(source = "layoutId", target = "layout")
    @Mapping(source = "sectionId", target = "section")
    @Mapping(target = "sensors", ignore = true)
    SectionSensorMapping toEntity(SectionSensorMappingDTO sectionSensorMappingDTO);

    default SectionSensorMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        SectionSensorMapping sectionSensorMapping = new SectionSensorMapping();
        sectionSensorMapping.setId(id);
        return sectionSensorMapping;
    }
}
