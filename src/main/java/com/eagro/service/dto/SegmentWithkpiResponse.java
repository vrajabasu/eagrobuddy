package com.eagro.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation of selection section details with optimal kpi values.
 */
@ApiModel(description = "Representation of selection section details with optimal kpi values.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class SegmentWithkpiResponse   {
  @JsonProperty("segmentName")
  private String segmentName = null;

  @JsonProperty("segmentDescription")
  private String segmentDescription = null;

  @JsonProperty("zoneWithKpis")
  @Valid
  private List<ZonewithkpisResponseDTO> zoneWithKpis = null;

  public SegmentWithkpiResponse segmentName(String segmentName) {
    this.segmentName = segmentName;
    return this;
  }

  /**
   * The name of the segment.
   * @return segmentName
  **/
  @ApiModelProperty(value = "The name of the segment.")


  public String getSegmentName() {
    return segmentName;
  }

  public void setSegmentName(String segmentName) {
    this.segmentName = segmentName;
  }

  public SegmentWithkpiResponse segmentDescription(String segmentDescription) {
    this.segmentDescription = segmentDescription;
    return this;
  }

  /**
   * Get segmentDescription
   * @return segmentDescription
  **/
  @ApiModelProperty(value = "")


  public String getSegmentDescription() {
    return segmentDescription;
  }

  public void setSegmentDescription(String segmentDescription) {
    this.segmentDescription = segmentDescription;
  }

  public SegmentWithkpiResponse zoneWithKpis(List<ZonewithkpisResponseDTO> zoneWithKpis) {
    this.zoneWithKpis = zoneWithKpis;
    return this;
  }

  public SegmentWithkpiResponse addZoneWithKpisItem(ZonewithkpisResponseDTO zoneWithKpisItem) {
    if (this.zoneWithKpis == null) {
      this.zoneWithKpis = new ArrayList<ZonewithkpisResponseDTO>();
    }
    this.zoneWithKpis.add(zoneWithKpisItem);
    return this;
  }

  /**
   * Represnts a list of zone with kpi details for specific sections.
   * @return zoneWithKpis
  **/
  @ApiModelProperty(value = "Represnts a list of zone with kpi details for specific sections.")

  @Valid

  public List<ZonewithkpisResponseDTO> getZoneWithKpis() {
    return zoneWithKpis;
  }

  public void setZoneWithKpis(List<ZonewithkpisResponseDTO> zoneWithKpis) {
    this.zoneWithKpis = zoneWithKpis;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SegmentWithkpiResponse segmentWithkpiResponse = (SegmentWithkpiResponse) o;
    return Objects.equals(this.segmentName, segmentWithkpiResponse.segmentName) &&
        Objects.equals(this.segmentDescription, segmentWithkpiResponse.segmentDescription) &&
        Objects.equals(this.zoneWithKpis, segmentWithkpiResponse.zoneWithKpis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentName, segmentDescription, zoneWithKpis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sectionwithkpi1 {\n");
    
    sb.append("    segmentName: ").append(toIndentedString(segmentName)).append("\n");
    sb.append("    segmentDescription: ").append(toIndentedString(segmentDescription)).append("\n");
    sb.append("    zoneWithKpis: ").append(toIndentedString(zoneWithKpis)).append("\n");
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

