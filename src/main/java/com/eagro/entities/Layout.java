package com.eagro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Layout entity.
 */
@Entity
@Table(name = "layout")
public class Layout implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_layout")
	@SequenceGenerator(name = "sequence_layout", sequenceName = "sequence_layout", allocationSize = 1)
	@Column(name = "layout_id")
	private Long layoutId;

	@Column(name = "layout_name")
	private String layoutName;

	@Column(name = "layout_desc")
	private String layoutDesc;

	@Column(name = "width_x")
	private Double widthX;

	@Column(name = "height_y")
	private Double heightY;

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

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<Section> sections = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<KPI> kPIS = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<SectionSensorMapping> sectionSensorMappings = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<SensorCoverageRange> sensorCoverageRanges = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<Segment> segments = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<Sensor> sensors = new HashSet<>();

	@OneToMany(mappedBy = "layout")
	@JsonIgnore
	private Set<SensorData> sensorData = new HashSet<>();

	@OneToOne
	@JsonIgnore
	private UserLayoutMapping userLayoutMapping;

	public Long getLayoutId() {
		return layoutId;
	}

	public Layout layoutId(Long layoutId) {
		this.layoutId = layoutId;
		return this;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public Layout layoutName(String layoutName) {
		this.layoutName = layoutName;
		return this;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public String getLayoutDesc() {
		return layoutDesc;
	}

	public Layout layoutDesc(String layoutDesc) {
		this.layoutDesc = layoutDesc;
		return this;
	}

	public void setLayoutDesc(String layoutDesc) {
		this.layoutDesc = layoutDesc;
	}

	public Double getWidthX() {
		return widthX;
	}

	public Layout widthX(Double widthX) {
		this.widthX = widthX;
		return this;
	}

	public void setWidthX(Double widthX) {
		this.widthX = widthX;
	}

	public Double getHeightY() {
		return heightY;
	}

	public Layout heightY(Double heightY) {
		this.heightY = heightY;
		return this;
	}

	public void setHeightY(Double heightY) {
		this.heightY = heightY;
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

	public Layout createdDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Layout createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public Layout updatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
		return this;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Layout updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public Layout sections(Set<Section> sections) {
		this.sections = sections;
		return this;
	}

	public Layout addSection(Section section) {
		this.sections.add(section);
		section.setLayout(this);
		return this;
	}

	public Layout removeSection(Section section) {
		this.sections.remove(section);
		section.setLayout(null);
		return this;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}

	public Set<KPI> getKPIS() {
		return kPIS;
	}

	public Layout kPIS(Set<KPI> kPIS) {
		this.kPIS = kPIS;
		return this;
	}

	public Layout addKPI(KPI kPI) {
		this.kPIS.add(kPI);
		kPI.setLayout(this);
		return this;
	}

	public Layout removeKPI(KPI kPI) {
		this.kPIS.remove(kPI);
		kPI.setLayout(null);
		return this;
	}

	public void setKPIS(Set<KPI> kPIS) {
		this.kPIS = kPIS;
	}

	public Set<SectionSensorMapping> getSectionSensorMappings() {
		return sectionSensorMappings;
	}

	public Layout sectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
		this.sectionSensorMappings = sectionSensorMappings;
		return this;
	}

	public Layout addSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
		this.sectionSensorMappings.add(sectionSensorMapping);
		sectionSensorMapping.setLayout(this);
		return this;
	}

	public Layout removeSectionSensorMapping(SectionSensorMapping sectionSensorMapping) {
		this.sectionSensorMappings.remove(sectionSensorMapping);
		sectionSensorMapping.setLayout(null);
		return this;
	}

	public void setSectionSensorMappings(Set<SectionSensorMapping> sectionSensorMappings) {
		this.sectionSensorMappings = sectionSensorMappings;
	}

	public Set<SensorCoverageRange> getSensorCoverageRanges() {
		return sensorCoverageRanges;
	}

	public Layout sensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
		this.sensorCoverageRanges = sensorCoverageRanges;
		return this;
	}

	public Layout addSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
		this.sensorCoverageRanges.add(sensorCoverageRange);
		sensorCoverageRange.setLayout(this);
		return this;
	}

	public Layout removeSensorCoverageRange(SensorCoverageRange sensorCoverageRange) {
		this.sensorCoverageRanges.remove(sensorCoverageRange);
		sensorCoverageRange.setLayout(null);
		return this;
	}

	public void setSensorCoverageRanges(Set<SensorCoverageRange> sensorCoverageRanges) {
		this.sensorCoverageRanges = sensorCoverageRanges;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public Layout segments(Set<Segment> segments) {
		this.segments = segments;
		return this;
	}

	public Layout addSegment(Segment segment) {
		this.segments.add(segment);
		segment.setLayout(this);
		return this;
	}

	public Layout removeSegment(Segment segment) {
		this.segments.remove(segment);
		segment.setLayout(null);
		return this;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	public Set<Sensor> getSensors() {
		return sensors;
	}

	public Layout sensors(Set<Sensor> sensors) {
		this.sensors = sensors;
		return this;
	}

	public Layout addSensor(Sensor sensor) {
		this.sensors.add(sensor);
		sensor.setLayout(this);
		return this;
	}

	public Layout removeSensor(Sensor sensor) {
		this.sensors.remove(sensor);
		sensor.setLayout(null);
		return this;
	}

	public void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;
	}

	public Set<SensorData> getSensorData() {
		return sensorData;
	}

	public Layout sensorData(Set<SensorData> sensorData) {
		this.sensorData = sensorData;
		return this;
	}

	public Layout addSensorData(SensorData sensorData) {
		this.sensorData.add(sensorData);
		sensorData.setLayout(this);
		return this;
	}

	public Layout removeSensorData(SensorData sensorData) {
		this.sensorData.remove(sensorData);
		sensorData.setLayout(null);
		return this;
	}

	public void setSensorData(Set<SensorData> sensorData) {
		this.sensorData = sensorData;
	}

	public Set<KPI> getkPIS() {
		return kPIS;
	}

	public void setkPIS(Set<KPI> kPIS) {
		this.kPIS = kPIS;
	}

	public UserLayoutMapping getUserLayoutMapping() {
		return userLayoutMapping;
	}

	public void setUserLayoutMapping(UserLayoutMapping userLayoutMapping) {
		this.userLayoutMapping = userLayoutMapping;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Layout layout = (Layout) o;
		if (layout.getLayoutId() == null || getLayoutId() == null) {
			return false;
		}
		return Objects.equals(getLayoutId(), layout.getLayoutId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLayoutId());
	}

	@Override
	public String toString() {
		return "Layout{" + ", layoutId=" + getLayoutId() + ", layoutName='" + getLayoutName() + "'" + ", layoutDesc='"
				+ getLayoutDesc() + "'" + ", widthX=" + getWidthX() + ", heightY=" + getHeightY() + ", activeFlag='"
				+ isActiveFlag() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", createdBy='" + getCreatedBy()
				+ "'" + ", updatedDate='" + getUpdatedDate() + "'" + ", updatedBy='" + getUpdatedBy() + "'" + "}";
	}
}
