package io.swagger.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation a lsit of kpis applicable for one segment categorized by different zone types.
 */
@ApiModel(description = "Representation a lsit of kpis applicable for one segment categorized by different zone types.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-29T10:53:00.181Z")

public class KpiValuesForTheCurrentSegment   {
  @JsonProperty("kpiName")
  private String kpiName = null;

  @JsonProperty("lowerRange")
  private Double lowerRange = null;

  @JsonProperty("upperRange")
  private Double upperRange = null;

  @JsonProperty("average")
  private Double average = null;

  public KpiValuesForTheCurrentSegment kpiName(String kpiName) {
    this.kpiName = kpiName;
    return this;
  }

  /**
   * kpi name
   * @return kpiName
  **/
  @ApiModelProperty(value = "kpi name")


  public String getKpiName() {
    return kpiName;
  }

  public void setKpiName(String kpiName) {
    this.kpiName = kpiName;
  }

  public KpiValuesForTheCurrentSegment lowerRange(Double lowerRange) {
    this.lowerRange = lowerRange;
    return this;
  }

  /**
   * lowerRange value for the KPI
   * @return lowerRange
  **/
  @ApiModelProperty(value = "lowerRange value for the KPI")


  public Double getLowerRange() {
    return lowerRange;
  }

  public void setLowerRange(Double lowerRange) {
    this.lowerRange = lowerRange;
  }

  public KpiValuesForTheCurrentSegment upperRange(Double upperRange) {
    this.upperRange = upperRange;
    return this;
  }

  /**
   * upperRange value for the KPI
   * @return upperRange
  **/
  @ApiModelProperty(value = "upperRange value for the KPI")


  public Double getUpperRange() {
    return upperRange;
  }

  public void setUpperRange(Double upperRange) {
    this.upperRange = upperRange;
  }

  public KpiValuesForTheCurrentSegment average(Double average) {
    this.average = average;
    return this;
  }

  /**
   * average value for the KPI
   * @return average
  **/
  @ApiModelProperty(value = "average value for the KPI")


  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KpiValuesForTheCurrentSegment kpiValuesForTheCurrentSegment = (KpiValuesForTheCurrentSegment) o;
    return Objects.equals(this.kpiName, kpiValuesForTheCurrentSegment.kpiName) &&
        Objects.equals(this.lowerRange, kpiValuesForTheCurrentSegment.lowerRange) &&
        Objects.equals(this.upperRange, kpiValuesForTheCurrentSegment.upperRange) &&
        Objects.equals(this.average, kpiValuesForTheCurrentSegment.average);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kpiName, lowerRange, upperRange, average);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KpiValuesForTheCurrentSegment {\n");
    
    sb.append("    kpiName: ").append(toIndentedString(kpiName)).append("\n");
    sb.append("    lowerRange: ").append(toIndentedString(lowerRange)).append("\n");
    sb.append("    upperRange: ").append(toIndentedString(upperRange)).append("\n");
    sb.append("    average: ").append(toIndentedString(average)).append("\n");
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

