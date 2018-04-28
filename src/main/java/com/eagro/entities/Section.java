package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Sections entity.
 */
@Entity
@Table(name = "section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "section_desc")
    private String sectionDesc;

    @Column(name = "start_x")
    private Double startX;

    @Column(name = "start_y")
    private Double startY;

    @Column(name = "end_x")
    private Double endX;

    @Column(name = "end_y")
    private Double endY;

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

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    private Set<Segment> segments = new HashSet<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    private Set<KPI> kPIS = new HashSet<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    private Set<SectionSensorMapping> sectionSensorMappings = new HashSet<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    private Set<SensorCoverageRange> sensorCoverageRanges = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "layout_id", nullable = false)
    private Layout layout;


    public Long getSectionId() {
        return sectionId;
    }

    public Section sectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public Section sectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionDesc() {
        return sectionDesc;
    }

    public Section sectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
        return this;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
    }

    public Double getStartX() {
        return startX;
    }

    public Section startX(Double startX) {
        this.startX = startX;
        return this;
    }

    public void setStartX(Double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return startY;
    }

    public Section startY(Double startY) {
        this.startY = startY;
        return this;
    }

    public void setStartY(Double startY) {
        this.startY = startY;
    }

    public Double getEndX() {
        return endX;
    }

    public Section endX(Double endX) {
        this.endX = endX;
        return this;
    }

    public void setEndX(Double endX) {
        this.endX = endX;
    }

    public Double getEndY() {
        return endY;
    }

    public Section endY(Double endY) {
        this.endY = endY;
        return this;
    }

    public void setEndY(Double endY) {
        this.endY = endY;
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

    public Section createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Section createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Section updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Section updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public Section segments(Set<Segment> segments) {
        this.segments = segments;
        return this;
    }

    public Section addSegment(Segment segment) {
        this.segments.add(segment);
        segment.setSection(this);
        return this;
    }

    public Section removeSegment(Segment segment) {
        this.segments.remove(segment);
        segment.setSection(null);
        return this;
    }

    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    public Set<KPI> getKPIS() {
        return kPIS;
    }

    public Section kPIS(Set<KPI> kPIS) {
        this.kPIS = kPIS;
        return this;
    }

    public Section addKPI(KPI kPI) {
        this.kPIS.add(kPI);
        kPI.setSection(this);
        return this;
    }

    public Section removeKPI(KPI kPI) {
        this.kPIS.remove(kPI);
        kPI.setSection(null);
        return this;
    }

    public void setKPIS(Set<KPI> kPIS) {
        this.kPIS = kPIS;
    }

    public Set<SectionSensorMapping> getSectionSensorMappings() {
        return sectionSensorMappings;
    }

    public Section sectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
        this.sectionSensorMappings = sectionSensorMappings;
        return this;
    }

    public Section addSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
        this.sectionSensorMappings.add(sectionSensorMapping);
        sectionSensorMapping.setSection(this);
        return this;
    }

    public Section removeSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
        this.sectionSensorMappings.remove(sectionSensorMapping);
        sectionSensorMapping.setSection(null);
        return this;
    }

    public void setSectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
        this.sectionSensorMappings = sectionSensorMappings;
    }

    public Set<SensorCoverageRange> getSensorCoverageRanges() {
        return sensorCoverageRanges;
    }

    public Section sensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
        this.sensorCoverageRanges = sensorCoverageRanges;
        return this;
    }

    public Section addSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
        this.sensorCoverageRanges.add(sensorCoverageRange);
        sensorCoverageRange.setSection(this);
        return this;
    }

    public Section removeSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
        this.sensorCoverageRanges.remove(sensorCoverageRange);
        sensorCoverageRange.setSection(null);
        return this;
    }

    public void setSensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
        this.sensorCoverageRanges = sensorCoverageRanges;
    }

    public Layout getLayout() {
        return layout;
    }

    public Section layout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        if (section.getSectionId() == null || getSectionId() == null) {
            return false;
        }
        return Objects.equals(getSectionId(), section.getSectionId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSectionId());
    }

    @Override
    public String toString() {
        return "Section{" +
            ", sectionId=" + getSectionId() +
            ", sectionName='" + getSectionName() + "'" +
            ", sectionDesc='" + getSectionDesc() + "'" +
            ", startX=" + getStartX() +
            ", startY=" + getStartY() +
            ", endX=" + getEndX() +
            ", endY=" + getEndY() +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
