package com.eagro.service.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation a list of zone with kpis for one section.
 */

public class OptimalKpiValueResponseDTO {
	@JsonProperty("kpiName")
	private String kpiName = null;

	@JsonProperty("optimalValueRange")
	private Double optimalValueRange = null;

	public OptimalKpiValueResponseDTO kpiName(String kpiName) {
		this.kpiName = kpiName;
		return this;
	}

	/**
	 * specify the threshold name
	 * 
	 * @return kpiName
	 **/
	@ApiModelProperty(value = "specify the threshold name")

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public OptimalKpiValueResponseDTO optimalValueRange(Double optimalValueRange) {
		this.optimalValueRange = optimalValueRange;
		return this;
	}

	/**
	 * Get optimalValueRange
	 * 
	 * @return optimalValueRange
	 **/
	@ApiModelProperty(value = "")

	public Double getOptimalValueRange() {
		return optimalValueRange;
	}

	public void setOptimalValueRange(Double optimalValueRange) {
		this.optimalValueRange = optimalValueRange;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OptimalKpiValueResponseDTO optimalKpiValueResponseDTO = (OptimalKpiValueResponseDTO) o;
		return Objects.equals(this.kpiName, optimalKpiValueResponseDTO.kpiName)
				&& Objects.equals(this.optimalValueRange, optimalKpiValueResponseDTO.optimalValueRange);
	}

	@Override
	public int hashCode() {
		return Objects.hash(kpiName, optimalValueRange);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AverageValue {\n");

		sb.append("    kpiName: ").append(toIndentedString(kpiName)).append("\n");
		sb.append("    optimalValueRange: ").append(toIndentedString(optimalValueRange)).append("\n");
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
