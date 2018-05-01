package com.eagro.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation of selection section details with optimal kpi values.
 */

public class SectionwithkpiResponseDTO   {
  @JsonProperty("sectionName")
  private String sectionName = null;

  @JsonProperty("sectionDescription")
  private String sectionDescription = null;

  @JsonProperty("zoneWithKpis")
  @Valid
  private List<ZonewithkpisResponseDTO> zoneWithKpis = null;

  public SectionwithkpiResponseDTO sectionName(String sectionName) {
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

  public SectionwithkpiResponseDTO sectionDescription(String sectionDescription) {
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

  public SectionwithkpiResponseDTO zoneWithKpis(List<ZonewithkpisResponseDTO> zoneWithKpis) {
    this.zoneWithKpis = zoneWithKpis;
    return this;
  }

  public SectionwithkpiResponseDTO addZoneWithKpisItem(ZonewithkpisResponseDTO zoneWithKpisItem) {
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
    SectionwithkpiResponseDTO sectionwithkpiResponseDTO = (SectionwithkpiResponseDTO) o;
    return Objects.equals(this.sectionName, sectionwithkpiResponseDTO.sectionName) &&
        Objects.equals(this.sectionDescription, sectionwithkpiResponseDTO.sectionDescription) &&
        Objects.equals(this.zoneWithKpis, sectionwithkpiResponseDTO.zoneWithKpis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sectionName, sectionDescription, zoneWithKpis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sectionwithkpi {\n");
    
    sb.append("    sectionName: ").append(toIndentedString(sectionName)).append("\n");
    sb.append("    sectionDescription: ").append(toIndentedString(sectionDescription)).append("\n");
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

