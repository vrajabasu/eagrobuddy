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

/**
 * Representation a lsit of segments for one section.
 */
@ApiModel(description = "Representation a lsit of segments for one section.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class Zones   {
  /**
   * Type of the zone holds aligned to the segment
   */
 
  @JsonProperty("key")
  private ZoneType key = null;

  @JsonProperty("sensors")
  @Valid
  private List<SensorsResponse> sensorsResponse = null;

  public Zones key(ZoneType key) {
    this.key = key;
    return this;
  }

  /**
   * Type of the zone holds aligned to the segment
   * @return key
  **/
  @ApiModelProperty(value = "Type of the zone holds aligned to the segment")


  public ZoneType getKey() {
    return key;
  }

  public void setKey(ZoneType key) {
    this.key = key;
  }

  public Zones sensorsResponse(List<SensorsResponse> sensorsResponse) {
    this.sensorsResponse = sensorsResponse;
    return this;
  }

  public Zones addSensorsItem(SensorsResponse sensorsItem) {
    if (this.sensorsResponse == null) {
      this.sensorsResponse = new ArrayList<SensorsResponse>();
    }
    this.sensorsResponse.add(sensorsItem);
    return this;
  }

  /**
   * Represnts a list of zone based sensor positioning data along with individual sensor threshold state.
   * @return sensors
  **/
  @ApiModelProperty(value = "Represnts a list of zone based sensor positioning data along with individual sensor threshold state.")

  @Valid

  public List<SensorsResponse> getSensors() {
    return sensorsResponse;
  }

  public void setSensors(List<SensorsResponse> sensorsResponse) {
    this.sensorsResponse = sensorsResponse;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Zones zones = (Zones) o;
    return Objects.equals(this.key, zones.key) &&
        Objects.equals(this.sensorsResponse, zones.sensorsResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, sensorsResponse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Zones {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    sensors: ").append(toIndentedString(sensorsResponse)).append("\n");
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

