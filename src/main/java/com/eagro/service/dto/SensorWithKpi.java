package com.eagro.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.eagro.entities.enumeration.ZoneType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation of selection section details with optimal kpi values.
 */
@ApiModel(description = "Representation of selection section details with optimal kpi values.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class SensorWithKpi   {
  @JsonProperty("segmentName")
  private String segmentName = null;

  @JsonProperty("segmentDescription")
  private String segmentDescription = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("time")
  private String time = null;

  @JsonProperty("sensorId")
  private Long sensorId = null;

  /**
   * Type of the zone holds the sensor positioning
   */
  public enum ZoneTypeEnum {
    WATER("WATER"),
    
    CANOPY("CANOPY"),
    
    ROOT("ROOT"),
    
    TOP("TOP");

    private String value;

    ZoneTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ZoneTypeEnum fromValue(String text) {
      for (ZoneTypeEnum b : ZoneTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("zoneType")
  private ZoneType zoneType = null;

  @JsonProperty("kpiValues")
  @Valid
  private List<OptimalKpiValueResponseDTO> kpiValues = null;

  public SensorWithKpi segmentName(String segmentName) {
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

  public SensorWithKpi segmentDescription(String segmentDescription) {
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

  public SensorWithKpi date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public SensorWithKpi time(String time) {
    this.time = time;
    return this;
  }

  /**
   * Get time
   * @return time
  **/
  @ApiModelProperty(value = "")


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public SensorWithKpi sensorId(Long sensorId) {
    this.sensorId = sensorId;
    return this;
  }

  /**
   * Get sensorId
   * @return sensorId
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Long getSensorId() {
    return sensorId;
  }

  public void setSensorId(Long sensorId) {
    this.sensorId = sensorId;
  }

  public SensorWithKpi zoneType(ZoneType zoneType) {
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

  public SensorWithKpi kpiValues(List<OptimalKpiValueResponseDTO> kpiValues) {
    this.kpiValues = kpiValues;
    return this;
  }

  public SensorWithKpi addKpiValuesItem(OptimalKpiValueResponseDTO kpiValuesItem) {
    if (this.kpiValues == null) {
      this.kpiValues = new ArrayList<OptimalKpiValueResponseDTO>();
    }
    this.kpiValues.add(kpiValuesItem);
    return this;
  }

  /**
   * Represnts a list of zone with kpi details for specific sections.
   * @return kpiValues
  **/
  @ApiModelProperty(value = "Represnts a list of zone with kpi details for specific sections.")

  @Valid

  public List<OptimalKpiValueResponseDTO> getKpiValues() {
    return kpiValues;
  }

  public void setKpiValues(List<OptimalKpiValueResponseDTO> kpiValues) {
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
    SensorWithKpi sensorWithKpi = (SensorWithKpi) o;
    return Objects.equals(this.segmentName, sensorWithKpi.segmentName) &&
        Objects.equals(this.segmentDescription, sensorWithKpi.segmentDescription) &&
        Objects.equals(this.date, sensorWithKpi.date) &&
        Objects.equals(this.time, sensorWithKpi.time) &&
        Objects.equals(this.sensorId, sensorWithKpi.sensorId) &&
        Objects.equals(this.zoneType, sensorWithKpi.zoneType) &&
        Objects.equals(this.kpiValues, sensorWithKpi.kpiValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentName, segmentDescription, date, time, sensorId, zoneType, kpiValues);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sectionwithkpi2 {\n");
    
    sb.append("    segmentName: ").append(toIndentedString(segmentName)).append("\n");
    sb.append("    segmentDescription: ").append(toIndentedString(segmentDescription)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    sensorId: ").append(toIndentedString(sensorId)).append("\n");
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

