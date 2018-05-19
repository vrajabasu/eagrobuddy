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
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentZoneDetailsResponse;
import com.eagro.service.dto.Segmentkpichart;
import com.eagro.service.dto.SensorWithKpi;
import com.eagro.service.utils.ResponseUtil;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * The Class describes the APIs required to implement the Green House Management
 * project with focus on Phase 1 implementation scope. These APIs lets the users
 * to retrieve information about green house in near real time from cloud data
 * source. Visualization section provides the complete set of APIs which will be
 * used to deliver the current status of the green house on screen. Color coding
 * is used to indicate the current status of the green house. User could easily
 * identify the area of green house which requires immediate attention from the
 * display.
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

	/**
	 * This service endpoint is used to retrieve the optimal KPI values that are
	 * applicable for the request section for the kpi entity. The response
	 * incluce the ideal value for comaring against the actual sensor data for
	 * various kpis in real time.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @return the optimal kpi
	 */
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

	/**
	 * This service endpoint is used to retrieve the current status of the
	 * segment. Response includes the actual sensor data captured near real time
	 * from sensors installed in farm, categorized based on different zone
	 * types.
	 * 
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @return the segment status
	 */
	@RequestMapping(value = "/eAgro/v1/visualization/segmentstatus/{layoutId}/sections/{sectionId}/segments/{segmentId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<SegmentWithkpiResponse> getSegmentStatus(
			@ApiParam(value = "Identifier for the layout", required = true) @PathVariable("layoutId") Long layoutId,
			@ApiParam(value = "Identifier for the section", required = true) @PathVariable("sectionId") Long sectionId,
			@ApiParam(value = "Identifier for the segment", required = true) @PathVariable("segmentId") Long segmentId) {
		log.info("REST request to get Segment status for sectionId:{}  mapped with layoutId:{} and segmentId : {}",
				sectionId, layoutId, segmentId);

		SegmentWithkpiResponse segmentWithkpiResponse = layoutVisualizationService.getSegmentStatus(layoutId, sectionId,
				segmentId);

		log.info("Segment Status  info : {} for Segment :{}  mapped with section : {} and layout:{} ",
				segmentWithkpiResponse, segmentId, sectionId, layoutId);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(segmentWithkpiResponse));

	}

	/**
	 * This service endpoint is used to retrieve the current sensor data from
	 * cloud data source for a particular sensor.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @param sensorId
	 *            the sensor id
	 * @return the sensor status
	 */
	@RequestMapping(value = "/eAgro/v1/visualization/sensorstatus/{layoutId}/sections/{sectionId}/segments/{segmentId}/sensors/{sensorId}", method = RequestMethod.GET)
	@Timed
	public ResponseEntity<SensorWithKpi> getSensorStatus(
			@ApiParam(value = "Identifier for the layout", required = true) @PathVariable("layoutId") Long layoutId,
			@ApiParam(value = "Identifier for the section", required = true) @PathVariable("sectionId") Long sectionId,
			@ApiParam(value = "Identifier for the segment", required = true) @PathVariable("segmentId") Long segmentId,
			@ApiParam(value = "Identifier for the sensor", required = true) @PathVariable("sensorId") Long sensorId) {
		log.info(
				"REST request to get Segment status for sectionId:{}  mapped with layoutId:{} , segmentId : {} and sensorId: {}",
				sectionId, layoutId, segmentId, sensorId);
		SensorWithKpi sensorWithKpiResponse = layoutVisualizationService.getSensorStatus(layoutId, sectionId, segmentId,
				sensorId);
		log.info(
				"Sensor Status  info : {} withs sensorId : {} for Segment :{}  mapped with section : {} and layout:{} ",
				sensorWithKpiResponse, sensorId, segmentId, sectionId, layoutId);

		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sensorWithKpiResponse));

	}

	/**
	 * This service endpoint is used to retrieve the current status of the farm
	 * categorized by different zone types. The reponse also includes current
	 * threshold state identified by different color codes. Green indicates
	 * segment is under normal condition. Yellow indicates that the section is
	 * performing with in the reference range but KPIs are outside deviation
	 * range specified and propability of moving out of reference range is
	 * likely. RED indicates that reading from sensors are outside optimal
	 * reference range for that particular segment.
	 *
	 * @param layoutId
	 *            the layout id
	 * @param sectionId
	 *            the section id
	 * @param segmentId
	 *            the segment id
	 * @return the zone status
	 */
	@RequestMapping(value = "/eAgro/v1/visualization/zonestatus/{layoutId}/sections/{sectionId}/segments/{segmentId}", method = RequestMethod.GET)
	ResponseEntity<SegmentZoneDetailsResponse> getZoneStatus(
			@ApiParam(value = "Identifier for the layout", required = true) @PathVariable("layoutId") Long layoutId,
			@ApiParam(value = "Identifier for the section", required = true) @PathVariable("sectionId") Long sectionId,
			@ApiParam(value = "Identifier for the segment", required = true) @PathVariable("segmentId") Long segmentId) {
		log.info("REST request to get Segment status for sectionId:{}  mapped with layoutId:{} , segmentId : {} ",
				sectionId, layoutId, segmentId);
		SegmentZoneDetailsResponse zoneStatusResponse = layoutVisualizationService.getZoneStatus(layoutId, sectionId,
				segmentId);

		log.info("Zone Status  info : {}  for Segment :{}  mapped with section : {} and layout:{} ", zoneStatusResponse,
				segmentId, sectionId, layoutId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zoneStatusResponse));
	}


	@RequestMapping(value = "/eAgro/v1/visualization/segmentkpichart/{layoutId}/sections/{sectionId}/segments/{segmentId}", method = RequestMethod.GET)
	ResponseEntity<Segmentkpichart> getSegmentKpiChart(
			@ApiParam(value = "Identifier for the layout", required = true) @PathVariable("layoutId") Long layoutId,
			@ApiParam(value = "Identifier for the section", required = true) @PathVariable("sectionId") Long sectionId,
			@ApiParam(value = "Identifier for the segment", required = true) @PathVariable("segmentId") Long segmentId) {
		log.info("REST request to get SegmentKpiChart for sectionId:{}  mapped with layoutId:{} , segmentId : {} ",
				sectionId, layoutId, segmentId);
		 Segmentkpichart segmentKpiChartValues = layoutVisualizationService.getSegmentKpiChartValues(layoutId, sectionId, segmentId);
		 log.info("REST Response SegmentKpiChart:{} for sectionId:{}  mapped with layoutId:{} , segmentId : {} ",
				 segmentKpiChartValues, sectionId, layoutId, segmentId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(segmentKpiChartValues));
	}

}