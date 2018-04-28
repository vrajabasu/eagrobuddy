package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "sensor_id")
	private Long sensorId;

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

	@OneToOne
	@JsonIgnore
	private SensorCoverageRange sensorCoverageRanges;

	@OneToOne
	@JsonIgnore
	private SectionSensorMapping sectionSensorMapping;

	@OneToOne
	@JsonIgnore
	private SensorData sensorData;

	@ManyToOne
	@JoinColumn(name = "layout_id", nullable = false)
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

	public Long getSensorId() {
		return sensorId;
	}

	public Sensor sensorId(Long sensorId) {
		this.sensorId = sensorId;
		return this;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
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

	public SensorCoverageRange getSensorCoverageRanges() {
		return sensorCoverageRanges;
	}

	public void setSensorCoverageRanges(SensorCoverageRange sensorCoverageRanges) {
		this.sensorCoverageRanges = sensorCoverageRanges;
	}

	public SectionSensorMapping getSectionSensorMapping() {
		return sectionSensorMapping;
	}

	public void setSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
		this.sectionSensorMapping = sectionSensorMapping;
	}

	public SensorData getSensorData() {
		return sensorData;
	}

	public void setSensorData(SensorData sensorData) {
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
		return "Sensor{" + "id=" + getId() + ", sensorName='" + getSensorName() + "'" + ", sensorDesc='"
				+ getSensorDesc() + "'" + ", activeFlag='" + isActiveFlag() + "'" + ", createdDate='" + getCreatedDate()
				+ "'" + ", createdBy='" + getCreatedBy() + "'" + ", updatedDate='" + getUpdatedDate() + "'"
				+ ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
