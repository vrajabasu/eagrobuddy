package com.eagro.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.entities.Section;
import com.eagro.entities.Segment;
import com.eagro.service.dto.SegmentDTO;

/**
 * Mapper for the entity Segment and its DTO SegmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LayoutMapper.class, SectionMapper.class})
public interface SegmentMapper extends EntityMapper<SegmentDTO, Segment> {

    @Mapping(source = "layout.layoutId", target = "layoutId")
    @Mapping(source = "section.sectionId", target = "sectionId")
    SegmentDTO toDto(Segment segment);

    @Mapping(source = "layoutId", target = "layout")
    @Mapping(source = "sectionId", target = "section")
    Segment toEntity(SegmentDTO segmentDTO);

    default Segment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Segment segment = new Segment();
        segment.setSegmentId(id);
        return segment;
    }

}
