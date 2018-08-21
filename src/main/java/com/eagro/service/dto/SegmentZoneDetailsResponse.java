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

  @JsonProperty("segmentStartX")
  private Double segmentStartX = null;

  @JsonProperty("segmentStartY")
  private Double segmentStartY = null;
  
  @JsonProperty("segmentEndX")
  private Double segmentEndX = null;

  @JsonProperty("segmentEndY")
  private Double segmentEndY = null;

  @JsonProperty("sectionStartX")
  private Double sectionStartX = null;

  @JsonProperty("sectionStartY")
  private Double sectionStartY = null;
  
  @JsonProperty("sectionEndX")
  private Double sectionEndX = null;

  @JsonProperty("sectionEndY")
  private Double sectionEndY = null;

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


/*  @Override
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
  }*/

  @Override
public String toString() {
	return "SegmentZoneDetailsResponse [sectionName=" + sectionName + ", sectionDescription=" + sectionDescription
			+ ", segmentName=" + segmentName + ", segmentDescription=" + segmentDescription + ", segmentStartX="
			+ segmentStartX + ", segmentStartY=" + segmentStartY + ", segmentEndX=" + segmentEndX + ", segmentEndY="
			+ segmentEndY + ", sectionStartX=" + sectionStartX + ", sectionStartY=" + sectionStartY + ", sectionEndX="
			+ sectionEndX + ", sectionEndY=" + sectionEndY + ", zones=" + zones + "]";
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((sectionDescription == null) ? 0 : sectionDescription.hashCode());
	result = prime * result + ((sectionEndX == null) ? 0 : sectionEndX.hashCode());
	result = prime * result + ((sectionEndY == null) ? 0 : sectionEndY.hashCode());
	result = prime * result + ((sectionName == null) ? 0 : sectionName.hashCode());
	result = prime * result + ((sectionStartX == null) ? 0 : sectionStartX.hashCode());
	result = prime * result + ((sectionStartY == null) ? 0 : sectionStartY.hashCode());
	result = prime * result + ((segmentDescription == null) ? 0 : segmentDescription.hashCode());
	result = prime * result + ((segmentEndX == null) ? 0 : segmentEndX.hashCode());
	result = prime * result + ((segmentEndY == null) ? 0 : segmentEndY.hashCode());
	result = prime * result + ((segmentName == null) ? 0 : segmentName.hashCode());
	result = prime * result + ((segmentStartX == null) ? 0 : segmentStartX.hashCode());
	result = prime * result + ((segmentStartY == null) ? 0 : segmentStartY.hashCode());
	result = prime * result + ((zones == null) ? 0 : zones.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	SegmentZoneDetailsResponse other = (SegmentZoneDetailsResponse) obj;
	if (sectionDescription == null) {
		if (other.sectionDescription != null)
			return false;
	} else if (!sectionDescription.equals(other.sectionDescription))
		return false;
	if (sectionEndX == null) {
		if (other.sectionEndX != null)
			return false;
	} else if (!sectionEndX.equals(other.sectionEndX))
		return false;
	if (sectionEndY == null) {
		if (other.sectionEndY != null)
			return false;
	} else if (!sectionEndY.equals(other.sectionEndY))
		return false;
	if (sectionName == null) {
		if (other.sectionName != null)
			return false;
	} else if (!sectionName.equals(other.sectionName))
		return false;
	if (sectionStartX == null) {
		if (other.sectionStartX != null)
			return false;
	} else if (!sectionStartX.equals(other.sectionStartX))
		return false;
	if (sectionStartY == null) {
		if (other.sectionStartY != null)
			return false;
	} else if (!sectionStartY.equals(other.sectionStartY))
		return false;
	if (segmentDescription == null) {
		if (other.segmentDescription != null)
			return false;
	} else if (!segmentDescription.equals(other.segmentDescription))
		return false;
	if (segmentEndX == null) {
		if (other.segmentEndX != null)
			return false;
	} else if (!segmentEndX.equals(other.segmentEndX))
		return false;
	if (segmentEndY == null) {
		if (other.segmentEndY != null)
			return false;
	} else if (!segmentEndY.equals(other.segmentEndY))
		return false;
	if (segmentName == null) {
		if (other.segmentName != null)
			return false;
	} else if (!segmentName.equals(other.segmentName))
		return false;
	if (segmentStartX == null) {
		if (other.segmentStartX != null)
			return false;
	} else if (!segmentStartX.equals(other.segmentStartX))
		return false;
	if (segmentStartY == null) {
		if (other.segmentStartY != null)
			return false;
	} else if (!segmentStartY.equals(other.segmentStartY))
		return false;
	if (zones == null) {
		if (other.zones != null)
			return false;
	} else if (!zones.equals(other.zones))
		return false;
	return true;
}

public Double getSegmentStartX() {
	return segmentStartX;
}

public void setSegmentStartX(Double segmentStartX) {
	this.segmentStartX = segmentStartX;
}

public Double getSegmentStartY() {
	return segmentStartY;
}

public void setSegmentStartY(Double segmentStartY) {
	this.segmentStartY = segmentStartY;
}

public Double getSegmentEndX() {
	return segmentEndX;
}

public void setSegmentEndX(Double segmentEndX) {
	this.segmentEndX = segmentEndX;
}

public Double getSegmentEndY() {
	return segmentEndY;
}

public void setSegmentEndY(Double segmentEndY) {
	this.segmentEndY = segmentEndY;
}

public Double getSectionStartX() {
	return sectionStartX;
}

public void setSectionStartX(Double sectionStartX) {
	this.sectionStartX = sectionStartX;
}

public Double getSectionStartY() {
	return sectionStartY;
}

public void setSectionStartY(Double sectionStartY) {
	this.sectionStartY = sectionStartY;
}

public Double getSectionEndX() {
	return sectionEndX;
}

public void setSectionEndX(Double sectionEndX) {
	this.sectionEndX = sectionEndX;
}

public Double getSectionEndY() {
	return sectionEndY;
}

public void setSectionEndY(Double sectionEndY) {
	this.sectionEndY = sectionEndY;
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

