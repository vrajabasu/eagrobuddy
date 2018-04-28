package com.eagro.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Sensor entity.
 */
public class SensorDTO implements Serializable {

    private Long id;

    private String sensorName;

    private String sensorDesc;

    private String activeFlag;

    private LocalDate createdDate;

    private String createdBy;

    private LocalDate updatedDate;

    private String updatedBy;

    private Set<SensorCoverageRangeDTO> sensorCoverageRanges = new HashSet<>();

    private Set<SectionSensorMappingDTO> sectionSensorMappings = new HashSet<>();

    private Set<SensorDataDTO> sensorData = new HashSet<>();

    private Long layoutId;

    private String layoutLayoutId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorDesc() {
        return sensorDesc;
    }

    public void setSensorDesc(String sensorDesc) {
        this.sensorDesc = sensorDesc;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<SensorCoverageRangeDTO> getSensorCoverageRanges() {
        return sensorCoverageRanges;
    }

    public void setSensorCoverageRanges(Set<SensorCoverageRangeDTO> sensorCoverageRanges) {
        this.sensorCoverageRanges = sensorCoverageRanges;
    }

    public Set<SectionSensorMappingDTO> getSectionSensorMappings() {
        return sectionSensorMappings;
    }

    public void setSectionSensorMappings(Set<SectionSensorMappingDTO> sectionSensorMappings) {
        this.sectionSensorMappings = sectionSensorMappings;
    }

    public Set<SensorDataDTO> getSensorData() {
        return sensorData;
    }

    public void setSensorData(Set<SensorDataDTO> sensorData) {
        this.sensorData = sensorData;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public String getLayoutLayoutId() {
        return layoutLayoutId;
    }

    public void setLayoutLayoutId(String layoutLayoutId) {
        this.layoutLayoutId = layoutLayoutId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SensorDTO sensorDTO = (SensorDTO) o;
        if(sensorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
            "id=" + getId() +
            ", sensorName='" + getSensorName() + "'" +
            ", sensorDesc='" + getSensorDesc() + "'" +
            ", activeFlag='" + getActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
