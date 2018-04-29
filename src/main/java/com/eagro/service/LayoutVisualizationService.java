package com.eagro.service;

import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionsResponseDTO;

public interface LayoutVisualizationService {

	/**
	 * Generate layout detail info for the given layoutId.
	 *
	 * @param layoutId
	 *            the layout id
	 * @return the layout response DTO
	 */
	LayoutResponseDTO getLayoutDetails(Long layoutId);

	/**
	 * Gets the section details info for the given section mapped with the
	 * specified layout.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @return the section details
	 */
	SectionsResponseDTO getSectionDetails(Long layoutId, Long sectionId);

}
