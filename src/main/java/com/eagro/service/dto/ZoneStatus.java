package com.eagro.service.dto;

import com.eagro.entities.enumeration.ZoneType;
import com.eagro.service.dto.enumeration.OverallThresholdstateEnum;

public class ZoneStatus {
	private Long sensorId;

	private OverallThresholdstateEnum thresholdState;

	private Double posX;

	private Double posY;
	private String sensorName;

	private String sensorDesc;
	private ZoneType zoneType = null;

	public Long getSensorId() {
		return sensorId;
	}

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	
	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorDesc() {
		return sensorDesc;
	}

	public void setSensorDesc(String sensorDesc) {
		this.sensorDesc = sensorDesc;
	}

	public OverallThresholdstateEnum getThresholdState() {
		return thresholdState;
	}

	public void setThresholdState(OverallThresholdstateEnum thresholdState) {
		this.thresholdState = thresholdState;
	}

	public ZoneType getZoneType() {
		return zoneType;
	}

	public void setZoneType(ZoneType zoneType) {
		this.zoneType = zoneType;
	}


}
