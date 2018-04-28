package com.eagro.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionSensorMappingDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SegmentDTO;
import com.eagro.service.dto.SegmentsResponseDTO;

@Mapper(componentModel = "spring")
public interface LayoutVisualizationMapper {
	
	/**
	 * @param layoutDTO
	 * @return
	 */
	@Mapping(target = "sections", ignore = true)
	LayoutResponseDTO layouttoLayoutResponse(LayoutDTO layoutDTO);
	
	@Mapping(target = "segments", ignore = true)
	SectionsResponseDTO sectiontoSectionResponse(SectionDTO sectionDTO);
	
	List<SectionsResponseDTO> sectiontoSectionResponseList(List<SectionDTO> sectionDTOList);
	
	SectionSensorMappingDTO sensorToSensor(SectionSensorMappingDTO sectionSensorMapping);

	@Mapping(target = "segmentDescription", source = "segmentDesc")
	@Mapping(target = "overallThresholdstate", ignore = true)
	SegmentsResponseDTO segmenttoSegmentsResponseDTO(SegmentDTO segment);

	
}
