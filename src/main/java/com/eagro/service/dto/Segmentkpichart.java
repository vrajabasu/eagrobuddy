package com.eagro.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.eagro.entities.enumeration.ZoneType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import io.swagger.model.KpiValuesForTheCurrentSegment;

/**
 * Representation a lsit of segments for one section.
 */
@ApiModel(description = "Representation a lsit of segments for one section.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class Segmentkpichart   {
  /**
   * Type of the zone applicable for a segment
   */
  
  @JsonProperty("zoneType")
  private ZoneType zoneType = null;

  @JsonProperty("kpiValues")
  @Valid
  private List<KpiValuesForTheCurrentSegment> kpiValues = null;

  public Segmentkpichart zoneType(ZoneType zoneType) {
    this.zoneType = zoneType;
    return this;
  }

  /**
   * Type of the zone applicable for a segment
   * @return zoneType
  **/
  @ApiModelProperty(value = "Type of the zone applicable for a segment")


  public ZoneType getZoneType() {
    return zoneType;
  }

  public void setZoneType(ZoneType zoneType) {
    this.zoneType = zoneType;
  }

  public Segmentkpichart kpiValues(List<KpiValuesForTheCurrentSegment> kpiValues) {
    this.kpiValues = kpiValues;
    return this;
  }

  public Segmentkpichart addKpiValuesItem(KpiValuesForTheCurrentSegment kpiValuesItem) {
    if (this.kpiValues == null) {
      this.kpiValues = new ArrayList<KpiValuesForTheCurrentSegment>();
    }
    this.kpiValues.add(kpiValuesItem);
    return this;
  }

  /**
   * Get kpiValues
   * @return kpiValues
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<KpiValuesForTheCurrentSegment> getKpiValues() {
    return kpiValues;
  }

  public void setKpiValues(List<KpiValuesForTheCurrentSegment> kpiValues) {
    this.kpiValues = kpiValues;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Segmentkpichart segmentkpichart = (Segmentkpichart) o;
    return Objects.equals(this.zoneType, segmentkpichart.zoneType) &&
        Objects.equals(this.kpiValues, segmentkpichart.kpiValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneType, kpiValues);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Segmentkpichart {\n");
    
    sb.append("    zoneType: ").append(toIndentedString(zoneType)).append("\n");
    sb.append("    kpiValues: ").append(toIndentedString(kpiValues)).append("\n");
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

