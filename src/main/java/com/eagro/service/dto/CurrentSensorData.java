package com.eagro.service.dto;

public class CurrentSensorData {

	private String kpiName;

    private Double kpiValue;
    
	private Long sensorId;

    private Long layoutId;
    
    public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public Double getKpiValue() {
		return kpiValue;
	}

	public void setKpiValue(Double kpiValue) {
		this.kpiValue = kpiValue;
	}

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	public Long getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Long layoutId) {
		this.layoutId = layoutId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kpiName == null) ? 0 : kpiName.hashCode());
		result = prime * result + ((kpiValue == null) ? 0 : kpiValue.hashCode());
		result = prime * result + ((layoutId == null) ? 0 : layoutId.hashCode());
		result = prime * result + ((sensorId == null) ? 0 : sensorId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentSensorData other = (CurrentSensorData) obj;
		if (kpiName == null) {
			if (other.kpiName != null)
				return false;
		} else if (!kpiName.equals(other.kpiName))
			return false;
		if (kpiValue == null) {
			if (other.kpiValue != null)
				return false;
		} else if (!kpiValue.equals(other.kpiValue))
			return false;
		if (layoutId == null) {
			if (other.layoutId != null)
				return false;
		} else if (!layoutId.equals(other.layoutId))
			return false;
		if (sensorId == null) {
			if (other.sensorId != null)
				return false;
		} else if (!sensorId.equals(other.sensorId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "currentSensorData [kpiName=" + kpiName + ", kpiValue=" + kpiValue + ", sensorId=" + sensorId
				+ ", layoutId=" + layoutId + "]";
	}

}
