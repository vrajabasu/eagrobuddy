package com.eagro.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.Section;
import com.eagro.service.dto.SectionDTO;

/**
 * Mapper for the entity Section and its DTO SectionDTO.
 */
@Mapper(componentModel = "spring", uses = {LayoutMapper.class})
public interface SectionMapper extends EntityMapper<SectionDTO, Section> {

    @Mapping(source = "layout.layoutId", target = "layoutLayoutId")
    SectionDTO toDto(Section section);

    @Mapping(target = "segments", ignore = true)
    @Mapping(target = "KPIS", ignore = true)
    @Mapping(target = "sectionSensorMappings", ignore = true)
    @Mapping(target = "sensorCoverageRanges", ignore = true)
    @Mapping(source = "layoutId", target = "layout")
    Section toEntity(SectionDTO sectionDTO);

    default Section fromId(Long id) {
        if (id == null) {
            return null;
        }
        Section section = new Section();
        section.setSectionId(id);
        return section;
    }
}
