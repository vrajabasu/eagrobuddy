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
 * Representation of one of the section details.
 */
@ApiModel(description = "Representation of one of the section details.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class SegmentZoneDetailsResponse   {
  @JsonProperty("sectionName")
  private String sectionName = null;

  @JsonProperty("sectionDescription")
  private String sectionDescription = null;

  @JsonProperty("segmentName")
  private String segmentName = null;

  @JsonProperty("segmentDescription")
  private String segmentDescription = null;

  @JsonProperty("segmentX")
  private Double segmentX = null;

  @JsonProperty("segmentY")
  private Double segmentY = null;

  @JsonProperty("sectionX")
  private Double sectionX = null;

  @JsonProperty("sectionY")
  private Double sectionY = null;

  @JsonProperty("zones")
  @Valid
  private List<Zones> zones = null;

  public SegmentZoneDetailsResponse sectionName(String sectionName) {
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

  public SegmentZoneDetailsResponse sectionDescription(String sectionDescription) {
    this.sectionDescription = sectionDescription;
    return this;
  }

  /**
   * Get sectionDescription
   * @return sectionDescription
  **/
  @ApiModelProperty(value = "")


  public String getSectionDescription() {
    return sectionDescription;
  }

  public void setSectionDescription(String sectionDescription) {
    this.sectionDescription = sectionDescription;
  }

  public SegmentZoneDetailsResponse segmentName(String segmentName) {
    this.segmentName = segmentName;
    return this;
  }

  /**
   * The name of the KPI.
   * @return segmentName
  **/
  @ApiModelProperty(value = "The name of the KPI.")


  public String getSegmentName() {
    return segmentName;
  }

  public void setSegmentName(String segmentName) {
    this.segmentName = segmentName;
  }

  public SegmentZoneDetailsResponse segmentDescription(String segmentDescription) {
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

  public SegmentZoneDetailsResponse segmentX(Double segmentX) {
    this.segmentX = segmentX;
    return this;
  }

  /**
   * The startX coordinates for specific section.
   * @return segmentX
  **/
  @ApiModelProperty(value = "The startX coordinates for specific section.")


  public Double getSegmentX() {
    return segmentX;
  }

  public void setSegmentX(Double segmentX) {
    this.segmentX = segmentX;
  }

  public SegmentZoneDetailsResponse segmentY(Double segmentY) {
    this.segmentY = segmentY;
    return this;
  }

  /**
   * The startY coordinates for specific section.
   * @return segmentY
  **/
  @ApiModelProperty(value = "The startY coordinates for specific section.")


  public Double getSegmentY() {
    return segmentY;
  }

  public void setSegmentY(Double segmentY) {
    this.segmentY = segmentY;
  }

  public SegmentZoneDetailsResponse sectionX(Double sectionX) {
    this.sectionX = sectionX;
    return this;
  }

  /**
   * The endX coordinates for specific section.
   * @return sectionX
  **/
  @ApiModelProperty(value = "The endX coordinates for specific section.")


  public Double getSectionX() {
    return sectionX;
  }

  public void setSectionX(Double sectionX) {
    this.sectionX = sectionX;
  }

  public SegmentZoneDetailsResponse sectionY(Double sectionY) {
    this.sectionY = sectionY;
    return this;
  }

  /**
   * The endY coordinates for specific section.
   * @return sectionY
  **/
  @ApiModelProperty(value = "The endY coordinates for specific section.")


  public Double getSectionY() {
    return sectionY;
  }

  public void setSectionY(Double sectionY) {
    this.sectionY = sectionY;
  }

  public SegmentZoneDetailsResponse zones(List<Zones> zones) {
    this.zones = zones;
    return this;
  }

  public SegmentZoneDetailsResponse addZonesItem(Zones zonesItem) {
    if (this.zones == null) {
      this.zones = new ArrayList<Zones>();
    }
    this.zones.add(zonesItem);
    return this;
  }

  /**
   * Represnts a list of zone based sensor positioning data along with individual sensor threshold state.
   * @return zones
  **/
  @ApiModelProperty(value = "Represnts a list of zone based sensor positioning data along with individual sensor threshold state.")

  @Valid

  public List<Zones> getZones() {
    return zones;
  }

  public void setZones(List<Zones> zones) {
    this.zones = zones;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SegmentZoneDetailsResponse segmentZoneDetailsResponse = (SegmentZoneDetailsResponse) o;
    return Objects.equals(this.sectionName, segmentZoneDetailsResponse.sectionName) &&
        Objects.equals(this.sectionDescription, segmentZoneDetailsResponse.sectionDescription) &&
        Objects.equals(this.segmentName, segmentZoneDetailsResponse.segmentName) &&
        Objects.equals(this.segmentDescription, segmentZoneDetailsResponse.segmentDescription) &&
        Objects.equals(this.segmentX, segmentZoneDetailsResponse.segmentX) &&
        Objects.equals(this.segmentY, segmentZoneDetailsResponse.segmentY) &&
        Objects.equals(this.sectionX, segmentZoneDetailsResponse.sectionX) &&
        Objects.equals(this.sectionY, segmentZoneDetailsResponse.sectionY) &&
        Objects.equals(this.zones, segmentZoneDetailsResponse.zones);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sectionName, sectionDescription, segmentName, segmentDescription, segmentX, segmentY, sectionX, sectionY, zones);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Segmentzonedetails {\n");
    
    sb.append("    sectionName: ").append(toIndentedString(sectionName)).append("\n");
    sb.append("    sectionDescription: ").append(toIndentedString(sectionDescription)).append("\n");
    sb.append("    segmentName: ").append(toIndentedString(segmentName)).append("\n");
    sb.append("    segmentDescription: ").append(toIndentedString(segmentDescription)).append("\n");
    sb.append("    segmentX: ").append(toIndentedString(segmentX)).append("\n");
    sb.append("    segmentY: ").append(toIndentedString(segmentY)).append("\n");
    sb.append("    sectionX: ").append(toIndentedString(sectionX)).append("\n");
    sb.append("    sectionY: ").append(toIndentedString(sectionY)).append("\n");
    sb.append("    zones: ").append(toIndentedString(zones)).append("\n");
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

