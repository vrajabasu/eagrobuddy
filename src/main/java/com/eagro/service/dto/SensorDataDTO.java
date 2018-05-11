package com.eagro.service.dto;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the SensorData entity.
 */
public class SensorDataDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private LocalDateTime recordedDateTime;

    private String param1;

    private Double paramValue1;

    private String param2;

    private Double paramValue2;

    private String param3;

    private Double paramValue3;
    
    private Long sensorId;

    private Long layoutId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRecordedDateTime() {
        return recordedDateTime;
    }

    public void setRecordedDateTime(LocalDateTime recordedDateTime) {
        this.recordedDateTime = recordedDateTime;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public Double getParamValue1() {
        return paramValue1;
    }

    public void setParamValue1(Double paramValue1) {
        this.paramValue1 = paramValue1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public Double getParamValue2() {
        return paramValue2;
    }

    public void setParamValue2(Double paramValue2) {
        this.paramValue2 = paramValue2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public Double getParamValue3() {
        return paramValue3;
    }

    public void setParamValue3(Double paramValue3) {
        this.paramValue3 = paramValue3;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SensorDataDTO sensorDataDTO = (SensorDataDTO) o;
        if(sensorDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorDataDTO{" +
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

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}
}
