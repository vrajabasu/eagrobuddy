package com.eagro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.eagro.entities.enumeration.ZoneType;

/**
 * SectionSensorMapping entity.
 */
@Entity
@Table(name = "section_sensor_mapping")
public class SectionSensorMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_section_sensor_mapping")
	@SequenceGenerator(name = "sequence_section_sensor_mapping", sequenceName = "sequence_section_sensor_mapping", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "zone_type")
	private ZoneType zoneType;

	@Column(name = "pos_x")
	private Double posX;

	@Column(name = "pos_y")
	private Double posY;

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

	@OneToOne
	@JoinColumn(name = "sensor_id", nullable = false)
	private Sensor sensor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public SectionSensorMapping zoneType(ZoneType zoneType) {
		this.zoneType = zoneType;
		return this;
	}

	public void setZoneType(ZoneType zoneType) {
		this.zoneType = zoneType;
	}

	public Double getPosX() {
		return posX;
	}

	public SectionSensorMapping posX(Double posX) {
		this.posX = posX;
		return this;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public SectionSensorMapping posY(Double posY) {
		this.posY = posY;
		return this;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public SectionSensorMapping createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public SectionSensorMapping createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public SectionSensorMapping updatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
		return this;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public SectionSensorMapping updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Layout getLayout() {
		return layout;
	}

	public SectionSensorMapping layout(Layout layout) {
		this.layout = layout;
		return this;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public Section getSection() {
		return section;
	}

	public SectionSensorMapping section(Section section) {
		this.section = section;
		return this;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SectionSensorMapping sectionSensorMapping = (SectionSensorMapping) o;
		if (sectionSensorMapping.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sectionSensorMapping.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SectionSensorMapping{" + "id=" + getId() + ", zoneType='" + getZoneType() + "'" + ", posX=" + getPosX()
				+ ", posY=" + getPosY() + ", createdDate='" + getCreatedDate() + "'" + ", createdBy='" + getCreatedBy()
				+ "'" + ", updatedDate='" + getUpdatedDate() + "'" + ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
