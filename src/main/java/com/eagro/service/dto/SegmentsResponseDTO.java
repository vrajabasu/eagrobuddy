package com.eagro.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.eagro.service.dto.enumeration.OverallThresholdstateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation a lsit of segments for one section.
 */
@ApiModel(description = "Representation a lsit of segments for one section.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-25T19:49:05.151Z")

public class SegmentsResponseDTO {
	@JsonProperty("segmentId")
	private BigDecimal segmentId = null;

	@JsonProperty("segmentName")
	private String segmentName = null;

	@JsonProperty("segmentDescription")
	private String segmentDescription = null;

	@JsonProperty("activeFlag")
	private Boolean activeFlag = null;

	@JsonProperty("startX")
	private Double startX = null;

	@JsonProperty("startY")
	private Double startY = null;

	@JsonProperty("endX")
	private Double endX = null;

	@JsonProperty("endY")
	private Double endY = null;

	@JsonProperty("overallThresholdstate")
	private OverallThresholdstateEnum overallThresholdstate = null;

	public SegmentsResponseDTO segmentId(BigDecimal segmentId) {
		this.segmentId = segmentId;
		return this;
	}

	/**
	 * Segment Id
	 * 
	 * @return segmentId
	 **/
	@ApiModelProperty(value = "Segment Id")

	@Valid

	public BigDecimal getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(BigDecimal segmentId) {
		this.segmentId = segmentId;
	}

	public SegmentsResponseDTO segmentName(String segmentName) {
		this.segmentName = segmentName;
		return this;
	}

	/**
	 * The name of the segment.
	 * 
	 * @return segmentName
	 **/
	@ApiModelProperty(value = "The name of the segment.")

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	public SegmentsResponseDTO segmentDescription(String segmentDescription) {
		this.segmentDescription = segmentDescription;
		return this;
	}

	/**
	 * Get segmentDescription
	 * 
	 * @return segmentDescription
	 **/
	@ApiModelProperty(value = "")

	public String getSegmentDescription() {
		return segmentDescription;
	}

	public void setSegmentDescription(String segmentDescription) {
		this.segmentDescription = segmentDescription;
	}

	public SegmentsResponseDTO activeFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
		return this;
	}

	/**
	 * The segment is currently active or not(e.g. 'ON' or 'OFF').
	 * 
	 * @return activeFlag
	 **/
	@ApiModelProperty(value = "The segment is currently active or not(e.g. 'ON' or 'OFF').")

	public Boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public SegmentsResponseDTO startX(Double startX) {
		this.startX = startX;
		return this;
	}

	/**
	 * The startX coordinates for specific segment.
	 * 
	 * @return startX
	 **/
	@ApiModelProperty(value = "The startX coordinates for specific segment.")

	public Double getStartX() {
		return startX;
	}

	public void setStartX(Double startX) {
		this.startX = startX;
	}

	public SegmentsResponseDTO startY(Double startY) {
		this.startY = startY;
		return this;
	}

	/**
	 * The startY coordinates for specific segment.
	 * 
	 * @return startY
	 **/
	@ApiModelProperty(value = "The startY coordinates for specific segment.")

	public Double getStartY() {
		return startY;
	}

	public void setStartY(Double startY) {
		this.startY = startY;
	}

	public SegmentsResponseDTO endX(Double endX) {
		this.endX = endX;
		return this;
	}

	/**
	 * The endX coordinates for specific segment.
	 * 
	 * @return endX
	 **/
	@ApiModelProperty(value = "The endX coordinates for specific segment.")

	public Double getEndX() {
		return endX;
	}

	public void setEndX(Double endX) {
		this.endX = endX;
	}

	public SegmentsResponseDTO endY(Double endY) {
		this.endY = endY;
		return this;
	}

	/**
	 * The endY coordinates for specific segment.
	 * 
	 * @return endY
	 **/
	@ApiModelProperty(value = "The endY coordinates for specific segment.")

	public Double getEndY() {
		return endY;
	}

	public void setEndY(Double endY) {
		this.endY = endY;
	}

	public SegmentsResponseDTO overallThresholdstate(OverallThresholdstateEnum overallThresholdstate) {
		this.overallThresholdstate = overallThresholdstate;
		return this;
	}

	/**
	 * Threshold value for layout
	 * 
	 * @return overallThresholdstate
	 **/
	@ApiModelProperty(value = "Threshold value for layout")

	public OverallThresholdstateEnum getOverallThresholdstate() {
		return overallThresholdstate;
	}

	public void setOverallThresholdstate(OverallThresholdstateEnum overallThresholdstate) {
		this.overallThresholdstate = overallThresholdstate;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SegmentsResponseDTO segmentsResponseDTO = (SegmentsResponseDTO) o;
		return Objects.equals(this.segmentId, segmentsResponseDTO.segmentId)
				&& Objects.equals(this.segmentName, segmentsResponseDTO.segmentName)
				&& Objects.equals(this.segmentDescription, segmentsResponseDTO.segmentDescription)
				&& Objects.equals(this.activeFlag, segmentsResponseDTO.activeFlag)
				&& Objects.equals(this.startX, segmentsResponseDTO.startX)
				&& Objects.equals(this.startY, segmentsResponseDTO.startY)
				&& Objects.equals(this.endX, segmentsResponseDTO.endX)
				&& Objects.equals(this.endY, segmentsResponseDTO.endY)
				&& Objects.equals(this.overallThresholdstate, segmentsResponseDTO.overallThresholdstate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(segmentId, segmentName, segmentDescription, activeFlag, startX, startY, endX, endY,
				overallThresholdstate);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Segments {\n");

		sb.append("    segmentId: ").append(toIndentedString(segmentId)).append("\n");
		sb.append("    segmentName: ").append(toIndentedString(segmentName)).append("\n");
		sb.append("    segmentDescription: ").append(toIndentedString(segmentDescription)).append("\n");
		sb.append("    activeFlag: ").append(toIndentedString(activeFlag)).append("\n");
		sb.append("    startX: ").append(toIndentedString(startX)).append("\n");
		sb.append("    startY: ").append(toIndentedString(startY)).append("\n");
		sb.append("    endX: ").append(toIndentedString(endX)).append("\n");
		sb.append("    endY: ").append(toIndentedString(endY)).append("\n");
		sb.append("    overallThresholdstate: ").append(toIndentedString(overallThresholdstate)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
