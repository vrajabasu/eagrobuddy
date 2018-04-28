package com.eagro.service;

import com.eagro.service.dto.LayoutResponseDTO;

public interface LayoutVisualizationService {

	
	/**
	 * Generate layout detail info for the given layoutId.
	 *
	 * @param layoutId the layout id
	 * @return the layout response DTO
	 */
	LayoutResponseDTO generateLayout(Long layoutId);

}
