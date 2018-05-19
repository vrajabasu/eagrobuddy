package com.eagro.service.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.eagro.entities.enumeration.ZoneType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation a list of zone with kpis for one section.
 */


public class ZonewithkpisResponseDTO   {
  /**
   * Type of the zone holds the sensor positioning
   */

  @JsonProperty("zoneType")
  private ZoneType zoneType = null;

  @JsonProperty("optimalKpiValues")
  @Valid
  private List<OptimalKpiValueResponseDTO> optimalKpiValues = null;

  public ZonewithkpisResponseDTO zoneType(ZoneType zoneType) {
    this.zoneType = zoneType;
    return this;
  }

  /**
   * Type of the zone holds the sensor positioning
   * @return zoneType
  **/
  @ApiModelProperty(value = "Type of the zone holds the sensor positioning")


  public ZoneType getZoneType() {
    return zoneType;
  }

  public void setZoneType(ZoneType zoneType) {
    this.zoneType = zoneType;
  }

  public ZonewithkpisResponseDTO optimalKpiValues(List<OptimalKpiValueResponseDTO> optimalKpiValues) {
    this.optimalKpiValues = optimalKpiValues;
    return this;
  }

  public ZonewithkpisResponseDTO addOptimalKpiValuesItem(OptimalKpiValueResponseDTO optimalKpiValuesItem) {
    if (this.optimalKpiValues == null) {
      this.optimalKpiValues = new ArrayList<OptimalKpiValueResponseDTO>();
    }
    this.optimalKpiValues.add(optimalKpiValuesItem);
    return this;
  }

  /**
   * Threshold value for layout
   * @return optimalKpiValues
  **/
  @ApiModelProperty(value = "Threshold value for layout")

  @Valid

  public List<OptimalKpiValueResponseDTO> getOptimalKpiValues() {
    return optimalKpiValues;
  }

  public void setOptimalKpiValues(List<OptimalKpiValueResponseDTO> optimalKpiValues) {
    this.optimalKpiValues = optimalKpiValues;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ZonewithkpisResponseDTO zonewithkpisResponseDTO = (ZonewithkpisResponseDTO) o;
    return Objects.equals(this.zoneType, zonewithkpisResponseDTO.zoneType) &&
        Objects.equals(this.optimalKpiValues, zonewithkpisResponseDTO.optimalKpiValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneType, optimalKpiValues);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Zonewithkpis {\n");
    
    sb.append("    zoneType: ").append(toIndentedString(zoneType)).append("\n");
    sb.append("    optimalKpiValues: ").append(toIndentedString(optimalKpiValues)).append("\n");
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

