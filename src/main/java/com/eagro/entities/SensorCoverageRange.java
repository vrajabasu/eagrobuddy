package com.eagro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * SensorCoverageRange entity.
 */
@Entity
@Table(name = "sensor_coverage_range")
public class SensorCoverageRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @ManyToOne
    private Layout layout;

    @ManyToOne
    private Section section;

    @ManyToMany(mappedBy = "sensorCoverageRanges")
    @JsonIgnore
    private Set<Sensor> sensors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStartX() {
        return startX;
    }

    public SensorCoverageRange startX(Double startX) {
        this.startX = startX;
        return this;
    }

    public void setStartX(Double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return startY;
    }

    public SensorCoverageRange startY(Double startY) {
        this.startY = startY;
        return this;
    }

    public void setStartY(Double startY) {
        this.startY = startY;
    }

    public Double getEndX() {
        return endX;
    }

    public SensorCoverageRange endX(Double endX) {
        this.endX = endX;
        return this;
    }

    public void setEndX(Double endX) {
        this.endX = endX;
    }

    public Double getEndY() {
        return endY;
    }

    public SensorCoverageRange endY(Double endY) {
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

    public SensorCoverageRange createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SensorCoverageRange createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public SensorCoverageRange updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SensorCoverageRange updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Layout getLayout() {
        return layout;
    }

    public SensorCoverageRange layout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Section getSection() {
        return section;
    }

    public SensorCoverageRange section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public SensorCoverageRange sensors(Set<Sensor> sensors) {
        this.sensors = sensors;
        return this;
    }

    public SensorCoverageRange addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.getSensorCoverageRanges().add(this);
        return this;
    }

    public SensorCoverageRange removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.getSensorCoverageRanges().remove(this);
        return this;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensorCoverageRange sensorCoverageRange = (SensorCoverageRange) o;
        if (sensorCoverageRange.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorCoverageRange.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorCoverageRange{" +
            "id=" + getId() +
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
