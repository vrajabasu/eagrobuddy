package com.eagro.entities;


import javax.persistence.*;

import com.eagro.entities.enumeration.ZoneType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * KPI entity.
 */

@Entity
@Table(name = "kpi")
public class KPI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_kpi")
    @SequenceGenerator(name = "sequence_kpi", sequenceName = "sequence_kpi", allocationSize = 1)
    @Column(name = "kpi_id")
    private Long kpiId;

    @Column(name = "kpi_name")
    private String kpiName;

    @Column(name = "kpi_desc")
    private String kpiDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone_type")
    private ZoneType zoneType;

    @Column(name = "lower_ref_limit")
    private Double lowerRefLimit;

    @Column(name = "upper_ref_limit")
    private Double upperRefLimit;

    @Column(name = "optimal_value")
    private Double optimalValue;

    @Column(name = "deviation_range")
    private Double deviationRange;

    @Column(name = "active_flag")
    private boolean activeFlag;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "layout_id", nullable = false)
    private Layout layout;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    public Long getKpiId() {
        return kpiId;
    }

    public KPI kpiId(Long kpiId) {
        this.kpiId = kpiId;
        return this;
    }

    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }

    public String getKpiName() {
        return kpiName;
    }

    public KPI kpiName(String kpiName) {
        this.kpiName = kpiName;
        return this;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public String getKpiDesc() {
        return kpiDesc;
    }

    public KPI kpiDesc(String kpiDesc) {
        this.kpiDesc = kpiDesc;
        return this;
    }

    public void setKpiDesc(String kpiDesc) {
        this.kpiDesc = kpiDesc;
    }

    public ZoneType getZoneType() {
        return zoneType;
    }

    public KPI zoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
        return this;
    }

    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    public Double getLowerRefLimit() {
        return lowerRefLimit;
    }

    public KPI lowerRefLimit(Double lowerRefLimit) {
        this.lowerRefLimit = lowerRefLimit;
        return this;
    }

    public void setLowerRefLimit(Double lowerRefLimit) {
        this.lowerRefLimit = lowerRefLimit;
    }

    public Double getUpperRefLimit() {
        return upperRefLimit;
    }

    public KPI upperRefLimit(Double upperRefLimit) {
        this.upperRefLimit = upperRefLimit;
        return this;
    }

    public void setUpperRefLimit(Double upperRefLimit) {
        this.upperRefLimit = upperRefLimit;
    }

    public Double getOptimalValue() {
        return optimalValue;
    }

    public KPI optimalValue(Double optimalValue) {
        this.optimalValue = optimalValue;
        return this;
    }

    public void setOptimalValue(Double optimalValue) {
        this.optimalValue = optimalValue;
    }

    public Double getDeviationRange() {
        return deviationRange;
    }

    public KPI deviationRange(Double deviationRange) {
        this.deviationRange = deviationRange;
        return this;
    }

    public void setDeviationRange(Double deviationRange) {
        this.deviationRange = deviationRange;
    }

    public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public LocalDate getCreatedDate() {
        return createdDate;
    }

    public KPI createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public KPI createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public KPI updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public KPI updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Layout getLayout() {
        return layout;
    }

    public KPI layout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Section getSection() {
        return section;
    }

    public KPI section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KPI kPI = (KPI) o;
        if (kPI.getKpiId() == null || getKpiId() == null) {
            return false;
        }
        return Objects.equals(getKpiId(), kPI.getKpiId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKpiId());
    }

    @Override
    public String toString() {
        return "KPI{" +
            ", kpiId=" + getKpiId() +
            ", kpiName='" + getKpiName() + "'" +
            ", kpiDesc='" + getKpiDesc() + "'" +
            ", zoneType='" + getZoneType() + "'" +
            ", lowerRefLimit=" + getLowerRefLimit() +
            ", upperRefLimit=" + getUpperRefLimit() +
            ", optimalValue=" + getOptimalValue() +
            ", deviationRange=" + getDeviationRange() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
