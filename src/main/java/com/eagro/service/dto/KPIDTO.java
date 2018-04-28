package com.eagro.service.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.eagro.entities.enumeration.ZoneType;

/**
 * A DTO for the KPI entity.
 */
public class KPIDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long kpiId;

    private String kpiName;

    private String kpiDesc;

    private ZoneType zoneType;

    private Double lowerRefLimit;

    private Double upperRefLimit;

    private Double optimalValue;

    private Double deviationRange;

    private String activeFlag;

    private LocalDate createdDate;

    private String createdBy;

    private LocalDate updatedDate;

    private String updatedBy;

    private Long layoutId;

    private Long sectionId;

    public Long getKpiId() {
        return kpiId;
    }

    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public String getKpiDesc() {
        return kpiDesc;
    }

    public void setKpiDesc(String kpiDesc) {
        this.kpiDesc = kpiDesc;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public Double getLowerRefLimit() {
        return lowerRefLimit;
    }

    public void setLowerRefLimit(Double lowerRefLimit) {
        this.lowerRefLimit = lowerRefLimit;
    }

    public Double getUpperRefLimit() {
        return upperRefLimit;
    }

    public void setUpperRefLimit(Double upperRefLimit) {
        this.upperRefLimit = upperRefLimit;
    }

    public Double getOptimalValue() {
        return optimalValue;
    }

    public void setOptimalValue(Double optimalValue) {
        this.optimalValue = optimalValue;
    }

    public Double getDeviationRange() {
        return deviationRange;
    }

    public void setDeviationRange(Double deviationRange) {
        this.deviationRange = deviationRange;
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

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KPIDTO kPIDTO = (KPIDTO) o;
        if(kPIDTO.getKpiId() == null || getKpiId() == null) {
            return false;
        }
        return Objects.equals(getKpiId(), kPIDTO.getKpiId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKpiId());
    }

    @Override
    public String toString() {
        return "KPIDTO{" +
            ", kpiId=" + getKpiId() +
            ", kpiName='" + getKpiName() + "'" +
            ", kpiDesc='" + getKpiDesc() + "'" +
            ", zoneType='" + getZoneType() + "'" +
            ", lowerRefLimit=" + getLowerRefLimit() +
            ", upperRefLimit=" + getUpperRefLimit() +
            ", optimalValue=" + getOptimalValue() +
            ", deviationRange=" + getDeviationRange() +
            ", activeFlag='" + getActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
