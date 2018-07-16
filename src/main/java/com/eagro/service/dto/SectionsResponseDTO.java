package com.eagro.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation of one of the section details.
 */


public class SectionsResponseDTO   {
  @JsonProperty("sectionId")
  private Long sectionId = null;

  @JsonProperty("sectionName")
  private String sectionName = null;

  @JsonProperty("sectionDescription")
  private String sectionDesc = null;

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

  @JsonProperty("segments")
  @Valid
  private List<SegmentsResponseDTO> segments = null;

  public SectionsResponseDTO sectionId(Long sectionId) {
    this.sectionId = sectionId;
    return this;
  }

  /**
   * The section Id
   * @return sectionId
  **/
  @ApiModelProperty(value = "The section Id")

  @Valid

  public Long getSectionId() {
    return sectionId;
  }

  public void setSectionId(Long sectionId) {
    this.sectionId = sectionId;
  }

  public SectionsResponseDTO sectionName(String sectionName) {
    this.sectionName = sectionName;
    return this;
  }

  /**
   * The name of the KPI.
   * @return sectionName
  **/
  @ApiModelProperty(value = "The name of the KPI.")


  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public SectionsResponseDTO sectionDescription(String sectionDescription) {
    this.sectionDesc = sectionDescription;
    return this;
  }

  /**
   * Get sectionDescription
   * @return sectionDescription
  **/
  @ApiModelProperty(value = "")


  public String getSectionDescription() {
    return sectionDesc;
  }

  public void setSectionDescription(String sectionDescription) {
    this.sectionDesc = sectionDescription;
  }

  public SectionsResponseDTO activeFlag(Boolean activeFlag) {
    this.activeFlag = activeFlag;
    return this;
  }

  /**
   * The KPI is currently active or not(e.g. 'ON' or 'OFF').
   * @return activeFlag
  **/
  @ApiModelProperty(value = "The KPI is currently active or not(e.g. 'ON' or 'OFF').")


  public Boolean isActiveFlag() {
    return activeFlag;
  }

  public void setActiveFlag(Boolean activeFlag) {
    this.activeFlag = activeFlag;
  }

  public SectionsResponseDTO startX(Double startX) {
    this.startX = startX;
    return this;
  }

  /**
   * The startX coordinates for specific section.
   * @return startX
  **/
  @ApiModelProperty(value = "The startX coordinates for specific section.")


  public Double getStartX() {
    return startX;
  }

  public void setStartX(Double startX) {
    this.startX = startX;
  }

  public SectionsResponseDTO startY(Double startY) {
    this.startY = startY;
    return this;
  }

  /**
   * The startY coordinates for specific section.
   * @return startY
  **/
  @ApiModelProperty(value = "The startY coordinates for specific section.")


  public Double getStartY() {
    return startY;
  }

  public void setStartY(Double startY) {
    this.startY = startY;
  }

  public SectionsResponseDTO endX(Double endX) {
    this.endX = endX;
    return this;
  }

  /**
   * The endX coordinates for specific section.
   * @return endX
  **/
  @ApiModelProperty(value = "The endX coordinates for specific section.")


  public Double getEndX() {
    return endX;
  }

  public void setEndX(Double endX) {
    this.endX = endX;
  }

  public SectionsResponseDTO endY(Double endY) {
    this.endY = endY;
    return this;
  }

  /**
   * The endY coordinates for specific section.
   * @return endY
  **/
  @ApiModelProperty(value = "The endY coordinates for specific section.")


  public Double getEndY() {
    return endY;
  }

  public void setEndY(Double endY) {
    this.endY = endY;
  }

  public SectionsResponseDTO segments(List<SegmentsResponseDTO> segmentsResponseDTO) {
    this.segments = segmentsResponseDTO;
    return this;
  }

  public SectionsResponseDTO addSegmentsItem(SegmentsResponseDTO segmentsItem) {
    if (this.segments == null) {
      this.segments = new ArrayList<SegmentsResponseDTO>();
    }
    this.segments.add(segmentsItem);
    return this;
  }

  /**
   * Represnts a list of segments for specific sections.
   * @return segments
  **/
  @ApiModelProperty(value = "Represnts a list of segments for specific sections.")

  @Valid

  public List<SegmentsResponseDTO> getSegments() {
    return segments;
  }

  public void setSegments(List<SegmentsResponseDTO> segmentsResponseDTO) {
    this.segments = segmentsResponseDTO;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SectionsResponseDTO sectionsResponseDTO = (SectionsResponseDTO) o;
    return Objects.equals(this.sectionId, sectionsResponseDTO.sectionId) &&
        Objects.equals(this.sectionName, sectionsResponseDTO.sectionName) &&
        Objects.equals(this.sectionDesc, sectionsResponseDTO.sectionDesc) &&
        Objects.equals(this.activeFlag, sectionsResponseDTO.activeFlag) &&
        Objects.equals(this.startX, sectionsResponseDTO.startX) &&
        Objects.equals(this.startY, sectionsResponseDTO.startY) &&
        Objects.equals(this.endX, sectionsResponseDTO.endX) &&
        Objects.equals(this.endY, sectionsResponseDTO.endY) &&
        Objects.equals(this.segments, sectionsResponseDTO.segments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sectionId, sectionName, sectionDesc, activeFlag, startX, startY, endX, endY, segments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sections1 {\n");
    
    sb.append("    sectionId: ").append(toIndentedString(sectionId)).append("\n");
    sb.append("    sectionName: ").append(toIndentedString(sectionName)).append("\n");
    sb.append("    sectionDescription: ").append(toIndentedString(sectionDesc)).append("\n");
    sb.append("    activeFlag: ").append(toIndentedString(activeFlag)).append("\n");
    sb.append("    startX: ").append(toIndentedString(startX)).append("\n");
    sb.append("    startY: ").append(toIndentedString(startY)).append("\n");
    sb.append("    endX: ").append(toIndentedString(endX)).append("\n");
    sb.append("    endY: ").append(toIndentedString(endY)).append("\n");
    sb.append("    segments: ").append(toIndentedString(segments)).append("\n");
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

