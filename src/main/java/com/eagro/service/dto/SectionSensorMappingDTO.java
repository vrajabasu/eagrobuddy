package com.eagro.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.eagro.entities.enumeration.ZoneType;

/**
 * A DTO for the SectionSensorMapping entity.
 */
public class SectionSensorMappingDTO implements Serializable {

    private Long id;

    private ZoneType zoneType;

    private Double posX;

    private Double posY;

    private LocalDate createdDate;

    private String createdBy;

    private LocalDate updatedDate;

    private String updatedBy;

    private Long layoutId;

    private String layoutLayoutId;

    private Long sectionId;

    private String sectionSectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
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

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionSectionId() {
        return sectionSectionId;
    }

    public void setSectionSectionId(String sectionSectionId) {
        this.sectionSectionId = sectionSectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SectionSensorMappingDTO sectionSensorMappingDTO = (SectionSensorMappingDTO) o;
        if(sectionSensorMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectionSensorMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectionSensorMappingDTO{" +
            "id=" + getId() +
            ", zoneType='" + getZoneType() + "'" +
            ", posX=" + getPosX() +
            ", posY=" + getPosY() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
