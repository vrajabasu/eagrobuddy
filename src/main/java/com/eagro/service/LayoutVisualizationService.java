package com.eagro.service;

import java.util.List;

import com.eagro.service.dto.LayoutResponseDTO;
import com.eagro.service.dto.SectionsResponseDTO;
import com.eagro.service.dto.SectionwithkpiResponseDTO;
import com.eagro.service.dto.SegmentWithkpiResponse;
import com.eagro.service.dto.SegmentZoneDetailsResponse;
import com.eagro.service.dto.Segmentkpichart;
import com.eagro.service.dto.SensorWithKpi;

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

	/**
	 * Gets the section based optimal kpi.
	 *
	 * @param sectionId
	 *            the section id
	 * @param layoutId the layout id
	 * @return the section based optimal kpi
	 */
	SectionwithkpiResponseDTO getSectionBasedOptimalKpi(Long sectionId, Long layoutId);

	/**
	 * Gets the segment status.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
	 * @param segmentId the segment id
	 * @return the segment status
	 */
	SegmentWithkpiResponse getSegmentStatus(Long layoutId, Long sectionId, Long segmentId);

	/**
	 * Gets the sensor status.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
	 * @param segmentId the segment id
	 * @param sensorId the sensor id
	 * @return the sensor status
	 */
	SensorWithKpi getSensorStatus(Long layoutId, Long sectionId, Long segmentId, Long sensorId);

	/**
	 * Gets the zone status.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
	 * @param segmentId the segment id
	 * @return the zone status
	 */
	SegmentZoneDetailsResponse getZoneStatus(Long layoutId, Long sectionId, Long segmentId);

	/**
	 * Gets the segment kpi chart values.
	 *
	 * @param layoutId the layout id
	 * @param sectionId the section id
	 * @param segmentId the segment id
	 * @return the segment kpi chart values
	 */
	Segmentkpichart getSegmentKpiChartValues(Long layoutId, Long sectionId, Long segmentId);

}
