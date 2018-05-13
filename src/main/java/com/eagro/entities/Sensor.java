package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Sensor entity.
 */
@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_sensor")
	@SequenceGenerator(name = "sequence_sensor", sequenceName = "sequence_sensor", allocationSize = 1)
	private Long id;

	@Column(name = "sensor_name")
	private String sensorName;

	@Column(name = "sensor_desc")
	private String sensorDesc;

	@Column(name = "sensor_id")
	private Long sensorId;

	@Column(name = "active_flag")
	private boolean activeFlag;

	@Column(name = "created_date", columnDefinition = "TIMESTAMP(3)")
	private LocalDateTime createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@ManyToOne
	@JoinColumn(name = "layout_id")
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
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
