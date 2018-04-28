package com.eagro.entities;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Sensor entity.
 */
@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "sensor_desc")
    private String sensorDesc;

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

    @ManyToMany
    @JoinTable(name = "sensor_sensor_coverage_range",
               joinColumns = @JoinColumn(name="sensors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="sensor_coverage_ranges_id", referencedColumnName="id"))
    private Set<SensorCoverageRange> sensorCoverageRanges = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sensor_section_sensor_mapping",
               joinColumns = @JoinColumn(name="sensors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="section_sensor_mappings_id", referencedColumnName="id"))
    private Set<SectionSensorMapping> sectionSensorMappings = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sensor_sensor_data",
               joinColumns = @JoinColumn(name="sensors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="sensor_data_id", referencedColumnName="id"))
    private Set<SensorData> sensorData = new HashSet<>();

    @ManyToOne
    private Layout layout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public Sensor sensorName(String sensorName) {
        this.sensorName = sensorName;
        return this;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorDesc() {
        return sensorDesc;
    }

    public Sensor sensorDesc(String sensorDesc) {
        this.sensorDesc = sensorDesc;
        return this;
    }

    public void setSensorDesc(String sensorDesc) {
        this.sensorDesc = sensorDesc;
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

    public Sensor createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Sensor createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Sensor updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Sensor updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<SensorCoverageRange> getSensorCoverageRanges() {
        return sensorCoverageRanges;
    }

    public Sensor sensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
        this.sensorCoverageRanges = sensorCoverageRanges;
        return this;
    }

    public Sensor addSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
        this.sensorCoverageRanges.add(sensorCoverageRange);
        sensorCoverageRange.getSensors().add(this);
        return this;
    }

    public Sensor removeSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
        this.sensorCoverageRanges.remove(sensorCoverageRange);
        sensorCoverageRange.getSensors().remove(this);
        return this;
    }

    public void setSensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
        this.sensorCoverageRanges = sensorCoverageRanges;
    }

    public Set<SectionSensorMapping> getSectionSensorMappings() {
        return sectionSensorMappings;
    }

    public Sensor sectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
        this.sectionSensorMappings = sectionSensorMappings;
        return this;
    }

    public Sensor addSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
        this.sectionSensorMappings.add(sectionSensorMapping);
        sectionSensorMapping.getSensors().add(this);
        return this;
    }

    public Sensor removeSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
        this.sectionSensorMappings.remove(sectionSensorMapping);
        sectionSensorMapping.getSensors().remove(this);
        return this;
    }

    public void setSectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
        this.sectionSensorMappings = sectionSensorMappings;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public Sensor sensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
        return this;
    }

    public Sensor addSensorData(SensorData sensorData) {
        this.sensorData.add(sensorData);
        sensorData.getSensors().add(this);
        return this;
    }

    public Sensor removeSensorData(SensorData sensorData) {
        this.sensorData.remove(sensorData);
        sensorData.getSensors().remove(this);
        return this;
    }

    public void setSensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public Layout getLayout() {
        return layout;
    }

    public Sensor layout(Layout layout) {
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
        Sensor sensor = (Sensor) o;
        if (sensor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + getId() +
            ", sensorName='" + getSensorName() + "'" +
            ", sensorDesc='" + getSensorDesc() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
