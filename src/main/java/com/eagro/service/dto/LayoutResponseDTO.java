package com.eagro.service.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representation of one of the layout details.
 */
@ApiModel(description = "Representation of one of the layout details.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-04-25T19:49:05.151Z")

public class LayoutResponseDTO {
	@JsonProperty("layoutId")
	private Long layoutId = null;

	@JsonProperty("layoutName")
	private String layoutName = null;

	@JsonProperty("layoutDescription")
	private String layoutDesc = null;

	@JsonProperty("activeFlag")
	private Boolean activeFlag = null;

	@JsonProperty("widthX")
	private Double widthX = null;

	@JsonProperty("heightY")
	private Double heightY = null;

	@JsonProperty("createdDate")
	private LocalDate createdDate = null;

	@JsonProperty("createdBy")
	private String createdBy = null;

	@JsonProperty("updatedDate")
	private LocalDate updatedDate = null;

	@JsonProperty("updatedBy")
	private String updatedBy = null;

	@JsonProperty("sections")
	@Valid
	private List<SectionsResponseDTO> sections = null;

	public LayoutResponseDTO layoutId(Long layoutId) {
		this.layoutId = layoutId;
		return this;
	}

	/**
	 * The layout id.
	 * 
	 * @return layoutId
	 **/
	@ApiModelProperty(value = "The layout id.")

	@Valid

	public Long getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	public LayoutResponseDTO layoutName(String layoutName) {
		this.layoutName = layoutName;
		return this;
	}

	/**
	 * The name of the Layout.
	 * 
	 * @return layoutName
	 **/
	@ApiModelProperty(value = "The name of the Layout.")

	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public LayoutResponseDTO activeFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
		return this;
	}

	/**
	 * The users is currently active or not(e.g. 'ON' or 'OFF').
	 * 
	 * @return activeFlag
	 **/
	@ApiModelProperty(value = "The users is currently active or not(e.g. 'ON' or 'OFF').")

	public Boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public LayoutResponseDTO widthX(Double widthX) {
		this.widthX = widthX;
		return this;
	}

	/**
	 * width specification of the layout in feet
	 * 
	 * @return widthX
	 **/
	@ApiModelProperty(value = "width specification of the layout in feet")

	public Double getWidthX() {
		return widthX;
	}

	public void setWidthX(Double widthX) {
		this.widthX = widthX;
	}

	public LayoutResponseDTO heightY(Double heightY) {
		this.heightY = heightY;
		return this;
	}

	/**
	 * height specification of the layout in feet
	 * 
	 * @return heightY
	 **/
	@ApiModelProperty(value = "height specification of the layout in feet")

	public Double getHeightY() {
		return heightY;
	}

	public void setHeightY(Double heightY) {
		this.heightY = heightY;
	}

	public LayoutResponseDTO createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	/**
	 * The layout creation date
	 * 
	 * @return createdDate
	 **/
	@ApiModelProperty(value = "The layout creation date")

	public LayoutResponseDTO createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	/**
	 * The users can do self registration or through admin.
	 * 
	 * @return createdBy
	 **/
	@ApiModelProperty(value = "The users can do self registration or through admin.")

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LayoutResponseDTO updatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
		return this;
	}

	/**
	 * The users preferred dateFormat (as formatString). Needs to match to a set
	 * of availableDateFormats
	 * 
	 * @return updatedDate
	 **/
	@ApiModelProperty(value = "The users preferred dateFormat (as formatString). Needs to match to a set of availableDateFormats")

	public LayoutResponseDTO updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	/**
	 * The updatation done either by Admin or self.
	 * 
	 * @return updatedBy
	 **/
	@ApiModelProperty(value = "The updatation done either by Admin or self.")

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LayoutResponseDTO sections(List<SectionsResponseDTO> sections) {
		this.sections = sections;
		return this;
	}

	public LayoutResponseDTO addSectionsItem(SectionsResponseDTO sectionsItem) {
		if (this.sections == null) {
			this.sections = new ArrayList<SectionsResponseDTO>();
		}
		this.sections.add(sectionsItem);
		return this;
	}

	public String getLayoutDesc() {
		return layoutDesc;
	}

	public void setLayoutDesc(String layoutDesc) {
		this.layoutDesc = layoutDesc;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * Represents a list of sections for the specific layout.
	 * 
	 * @return sections
	 **/
	@ApiModelProperty(value = "Represents a list of sections for the specific layout.")

	@Valid

	public List<SectionsResponseDTO> getSections() {
		return sections;
	}

	public void setSections(List<SectionsResponseDTO> sections) {
		this.sections = sections;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LayoutResponseDTO layoutResponseDTO = (LayoutResponseDTO) o;
		return Objects.equals(this.layoutId, layoutResponseDTO.layoutId)
				&& Objects.equals(this.layoutName, layoutResponseDTO.layoutName)
				&& Objects.equals(this.layoutDesc, layoutResponseDTO.layoutDesc)
				&& Objects.equals(this.activeFlag, layoutResponseDTO.activeFlag)
				&& Objects.equals(this.widthX, layoutResponseDTO.widthX)
				&& Objects.equals(this.heightY, layoutResponseDTO.heightY)
				&& Objects.equals(this.createdDate, layoutResponseDTO.createdDate)
				&& Objects.equals(this.createdBy, layoutResponseDTO.createdBy)
				&& Objects.equals(this.updatedDate, layoutResponseDTO.updatedDate)
				&& Objects.equals(this.updatedBy, layoutResponseDTO.updatedBy)
				&& Objects.equals(this.sections, layoutResponseDTO.sections);
	}

	@Override
	public int hashCode() {
		return Objects.hash(layoutId, layoutName, layoutDesc, activeFlag, widthX, heightY, createdDate, createdBy,
				updatedDate, updatedBy, sections);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Layout2 {\n");

		sb.append("    layoutId: ").append(toIndentedString(layoutId)).append("\n");
		sb.append("    layoutName: ").append(toIndentedString(layoutName)).append("\n");
		sb.append("    layoutDescription: ").append(toIndentedString(layoutDesc)).append("\n");
		sb.append("    activeFlag: ").append(toIndentedString(activeFlag)).append("\n");
		sb.append("    widthX: ").append(toIndentedString(widthX)).append("\n");
		sb.append("    heightY: ").append(toIndentedString(heightY)).append("\n");
		sb.append("    createdDate: ").append(toIndentedString(createdDate)).append("\n");
		sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
		sb.append("    updatedDate: ").append(toIndentedString(updatedDate)).append("\n");
		sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
		sb.append("    sections: ").append(toIndentedString(sections)).append("\n");
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
