package com.eagro.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eagro.service.dto.LayoutDTO;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionDTO;
import com.eagro.service.dto.SectionsResponseDTO;

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

	
}
