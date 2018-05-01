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

/**
 * SensorData entity.
 */
@Entity
@Table(name = "sensor_data")
public class SensorData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_sensor_data")
    @SequenceGenerator(name = "sequence_sensor_data", sequenceName = "sequence_sensor_data", allocationSize = 1)
    private Long id;

    @Column(name = "recorded_date_time")
    private LocalDate recordedDateTime;

    @Column(name = "param_1")
    private String param1;

    @Column(name = "param_value_1")
    private Double paramValue1;

    @Column(name = "param_2")
    private String param2;

    @Column(name = "param_value_2")
    private Double paramValue2;

    @Column(name = "param_3")
    private String param3;

    @Column(name = "param_value_3")
    private Double paramValue3;

    @ManyToOne
    @JoinColumn(name = "layout_id", nullable = false)
    private Layout layout;

    @OneToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRecordedDateTime() {
        return recordedDateTime;
    }

    public SensorData recordedDateTime(LocalDate recordedDateTime) {
        this.recordedDateTime = recordedDateTime;
        return this;
    }

    public void setRecordedDateTime(LocalDate recordedDateTime) {
        this.recordedDateTime = recordedDateTime;
    }

    public String getParam1() {
        return param1;
    }

    public SensorData param1(String param1) {
        this.param1 = param1;
        return this;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public Double getParamValue1() {
        return paramValue1;
    }

    public SensorData paramValue1(Double paramValue1) {
        this.paramValue1 = paramValue1;
        return this;
    }

    public void setParamValue1(Double paramValue1) {
        this.paramValue1 = paramValue1;
    }

    public String getParam2() {
        return param2;
    }

    public SensorData param2(String param2) {
        this.param2 = param2;
        return this;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public Double getParamValue2() {
        return paramValue2;
    }

    public SensorData paramValue2(Double paramValue2) {
        this.paramValue2 = paramValue2;
        return this;
    }

    public void setParamValue2(Double paramValue2) {
        this.paramValue2 = paramValue2;
    }

    public String getParam3() {
        return param3;
    }

    public SensorData param3(String param3) {
        this.param3 = param3;
        return this;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Double getParamValue3() {
        return paramValue3;
    }

    public SensorData paramValue3(Double paramValue3) {
        this.paramValue3 = paramValue3;
        return this;
    }

    public void setParamValue3(Double paramValue3) {
        this.paramValue3 = paramValue3;
    }

    public Layout getLayout() {
        return layout;
    }

    public SensorData layout(Layout layout) {
        this.layout = layout;
        return this;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
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
        SensorData sensorData = (SensorData) o;
        if (sensorData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorData{" +
            "id=" + getId() +
            ", recordedDateTime='" + getRecordedDateTime() + "'" +
            ", param1='" + getParam1() + "'" +
            ", paramValue1=" + getParamValue1() +
            ", param2='" + getParam2() + "'" +
            ", paramValue2=" + getParamValue2() +
            ", param3='" + getParam3() + "'" +
            ", paramValue3=" + getParamValue3() +
            "}";
    }
}
