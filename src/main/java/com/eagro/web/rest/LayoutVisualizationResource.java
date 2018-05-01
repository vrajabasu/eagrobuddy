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
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
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
	 * Gets the layout information used to retrieve overall layout visualization
	 * showing sections and segments with in the sections, as well current
	 * status of each section represented by colour coding. Green indicates
	 * segment is under normal condition.Yellow indicates that the section is
	 * performing with in the reference range but KPIs are outside deviation
	 * range specified and propability of moving out of reference range is
	 * likely.<br/>
	 * RED indicates that reading from sensors are outside optimal reference
	 * range for that particular segment.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @return the layout
	 */
	@RequestMapping(value = "/eAgro/v1/visualization/overall/layouts/{layoutId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<LayoutResponseDTO> getLayout(@PathVariable Long layoutId) {
		log.info("REST request to get Layout for layoutId:{} ", layoutId);
		// TODO user validation need to be implement
		LayoutResponseDTO finalLayoutResponse = layoutVisualizationService.getLayoutDetails(layoutId);
		log.info("Layout info : {} for layoutId:{} ", layoutId, finalLayoutResponse);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finalLayoutResponse));
	}

	/**
	 * This service endpoint is used to provide current status of the section
	 * based on the request section id within a particular layout. The color
	 * coding of each segment within the section represents the current
	 * condition of the segment. Green indicates segment is under normal
	 * condition. Yellow indicates that the section is performing with in the
	 * reference range but KPIs are outside deviation range specified and
	 * propability of moving out of reference range is likely. RED indicates
	 * that reading from sensors are outside optimal reference range for that
	 * particular segment.
	 * 
	 * @param layoutId
	 * @param sectionId
	 * @return
	 */
	@RequestMapping(value = "/eAgro/v1/visualization/overall/{layoutId}/sections/{sectionId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<SectionsResponseDTO> getSection(@PathVariable Long layoutId, @PathVariable Long sectionId) {
		log.info("REST request to get section for layoutId:{} and sectionId:{} ", layoutId, sectionId);
		// TODO user validation need to be implement
		SectionsResponseDTO finalSectionResponse = layoutVisualizationService.getSectionDetails(layoutId, sectionId);
		log.info("Section info : {} for section : {} mapped with layout:{} ", finalSectionResponse, sectionId,
				layoutId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finalSectionResponse));
	}

	@RequestMapping(value = "/eAgro/v1/visualization/optimalkpichart/{layoutId}/sections/{sectionId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<SectionwithkpiResponseDTO> getOptimalKpi(@PathVariable Long layoutId,
			@PathVariable Long sectionId) {
		// TODO user validation for sectionId
		log.info("REST request to get OptimalKpi for sectionId:{}  mapped with layoutId:{}", sectionId, layoutId);
		// TODO user validation need to be implement
		SectionwithkpiResponseDTO finalOptimalKpiResponse = layoutVisualizationService
				.getSectionBasedOptimalKpi(sectionId, layoutId);
		log.info("Section related KPI info : {} for section : {} mapped with layout:{} ", finalOptimalKpiResponse,
				sectionId, layoutId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(finalOptimalKpiResponse));
	}

}