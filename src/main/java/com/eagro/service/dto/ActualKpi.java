package com.eagro.service.dto;

import com.eagro.entities.enumeration.ZoneType;

public class ActualKpi {
	 private Long sensorId;
	 
	 private String param1;

	 private Double paramValue1;

	 private ZoneType zoneType;
	public Long getSensorId() {
		return sensorId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((paramValue1 == null) ? 0 : paramValue1.hashCode());
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
		ActualKpi other = (ActualKpi) obj;
		if (param1 == null) {
			if (other.param1 != null)
				return false;
		} else if (!param1.equals(other.param1))
			return false;
		if (paramValue1 == null) {
			if (other.paramValue1 != null)
				return false;
		} else if (!paramValue1.equals(other.paramValue1))
			return false;
		if (sensorId == null) {
			if (other.sensorId != null)
				return false;
		} else if (!sensorId.equals(other.sensorId))
			return false;
		return true;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
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

	@Override
	public String toString() {
		return "ActualKpi [sensorId=" + sensorId + ", param1=" + param1 + ", paramValue1=" + paramValue1 + "]";
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public void setZoneType(ZoneType zoneType) {
		this.zoneType = zoneType;
	}
}
