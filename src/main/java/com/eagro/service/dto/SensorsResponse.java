package com.eagro.service.dto;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class SensorsResponse   {
  @JsonProperty("sensorId")
  private Long sensorId = null;

  @JsonProperty("sensorName")
  private String sensorName = null;

  @JsonProperty("sensorDescription")
  private String sensorDescription = null;

  @JsonProperty("widthX")
  private Double widthX = null;

  @JsonProperty("heightY")
  private Double heightY = null;

  
  @JsonProperty("thresholdState")
  private OverallThresholdstateEnum thresholdState = null;

  public SensorsResponse sensorId(Long sensorId) {
    this.sensorId = sensorId;
    return this;
  }

  /**
   * The sensor id.
   * @return sensorId
  **/
  @ApiModelProperty(value = "The sensor id.")

  @Valid

  public Long getSensorId() {
    return sensorId;
  }

  public void setSensorId(Long sensorId) {
    this.sensorId = sensorId;
  }

  public SensorsResponse sensorName(String sensorName) {
    this.sensorName = sensorName;
    return this;
  }

  /**
   * The name of the sensor.
   * @return sensorName
  **/
  @ApiModelProperty(value = "The name of the sensor.")


  public String getSensorName() {
    return sensorName;
  }

  public void setSensorName(String sensorName) {
    this.sensorName = sensorName;
  }

  public SensorsResponse sensorDescription(String sensorDescription) {
    this.sensorDescription = sensorDescription;
    return this;
  }

  /**
   * Get sensorDescription
   * @return sensorDescription
  **/
  @ApiModelProperty(value = "")


  public String getSensorDescription() {
    return sensorDescription;
  }

  public void setSensorDescription(String sensorDescription) {
    this.sensorDescription = sensorDescription;
  }

  public SensorsResponse widthX(Double widthX) {
    this.widthX = widthX;
    return this;
  }

  /**
   * The widthX coordinates for specific sensorpostioning in section.
   * @return widthX
  **/
  @ApiModelProperty(value = "The widthX coordinates for specific sensorpostioning in section.")


  public Double getWidthX() {
    return widthX;
  }

  public void setWidthX(Double widthX) {
    this.widthX = widthX;
  }

  public SensorsResponse heightY(Double heightY) {
    this.heightY = heightY;
    return this;
  }

  /**
   * The heightY coordinates for specific sensorpostioning in   section.
   * @return heightY
  **/
  @ApiModelProperty(value = "The heightY coordinates for specific sensorpostioning in   section.")

  @Valid

  public Double getHeightY() {
    return heightY;
  }

  public void setHeightY(Double heightY) {
    this.heightY = heightY;
  }

  public SensorsResponse thresholdState(OverallThresholdstateEnum thresholdState) {
    this.thresholdState = thresholdState;
    return this;
  }

  /**
   * Threshold value of the zone holds aligned to the segment
   * @return thresholdState
  **/
  @ApiModelProperty(value = "Threshold value of the zone holds aligned to the segment")


  public OverallThresholdstateEnum getThresholdState() {
    return thresholdState;
  }

  public void setThresholdState(OverallThresholdstateEnum thresholdState) {
    this.thresholdState = thresholdState;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SensorsResponse sensorsResponse = (SensorsResponse) o;
    return Objects.equals(this.sensorId, sensorsResponse.sensorId) &&
        Objects.equals(this.sensorName, sensorsResponse.sensorName) &&
        Objects.equals(this.sensorDescription, sensorsResponse.sensorDescription) &&
        Objects.equals(this.widthX, sensorsResponse.widthX) &&
        Objects.equals(this.heightY, sensorsResponse.heightY) &&
        Objects.equals(this.thresholdState, sensorsResponse.thresholdState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sensorId, sensorName, sensorDescription, widthX, heightY, thresholdState);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sensors {\n");
    
    sb.append("    sensorId: ").append(toIndentedString(sensorId)).append("\n");
    sb.append("    sensorName: ").append(toIndentedString(sensorName)).append("\n");
    sb.append("    sensorDescription: ").append(toIndentedString(sensorDescription)).append("\n");
    sb.append("    widthX: ").append(toIndentedString(widthX)).append("\n");
    sb.append("    heightY: ").append(toIndentedString(heightY)).append("\n");
    sb.append("    thresholdState: ").append(toIndentedString(thresholdState)).append("\n");
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

