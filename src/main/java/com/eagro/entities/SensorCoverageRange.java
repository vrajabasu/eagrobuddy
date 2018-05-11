package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * SensorCoverageRange entity.
 */
@Entity
@Table(name = "sensor_coverage_range")
public class SensorCoverageRange implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_sensor_coverange_range")
	@SequenceGenerator(name = "sequence_sensor_coverange_range", sequenceName = "sequence_sensor_coverange_range", allocationSize = 1)
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

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "created_date", columnDefinition = "TIMESTAMP(3)", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@ManyToOne
	@JoinColumn(name = "layout_id", nullable = false)
	private Layout layout;

	@ManyToOne
	@JoinColumn(name = "section_id", nullable = false)
	private Section section;

	public Long getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(Long sensor_id) {
		this.sensor_id = sensor_id;
	}

	private Long sensor_id;

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
		return "SensorCoverageRange{" + "id=" + getId() + ", startX=" + getStartX() + ", startY=" + getStartY()
				+ ", endX=" + getEndX() + ", endY=" + getEndY() + ", activeFlag='" + isActiveFlag() + "'"
				+ ", createdDate='" + getCreatedDate() + "'" + ", createdBy='" + getCreatedBy() + "'"
				+ ", updatedDate='" + getUpdatedDate() + "'" + ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
