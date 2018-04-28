package com.eagro.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eagro.service.LayoutVisualizationService;
import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.utils.ResponseUtil;

/**
 * The Class LayoutVisualizationResource.
 */
@RestController
@RequestMapping("/api")
public class LayoutVisualizationResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(LayoutVisualizationResource.class);

	/** The layout visualization service. */
	@Autowired
	public LayoutVisualizationService layoutVisualizationService;

	/**
	 * Gets the layout information used to retrieve overall layout
	 * visualization showing sections and segments with in the sections, as well
	 * current status of each section represented by colour coding. Green
	 * indicates segment is under normal condition.Yellow indicates that the
	 * section is performing with in the reference range but KPIs are outside
	 * deviation range specified and propability of moving out of reference
	 * range is likely.<br/>
	 * RED indicates that reading from sensors are outside optimal reference
	 * range for that particular segment.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @return the layout
	 */
	@RequestMapping(value = "/eagro/v1/visualization/layout/{layoutId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<LayoutResponseDTO> getLayout(@PathVariable Long layoutId) {
		log.debug("REST request to get Layout for layoutId:{} ", layoutId);

		LayoutResponseDTO finalLayoutResponse = layoutVisualizationService.generateLayout(layoutId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finalLayoutResponse));
	}
	
	
}
