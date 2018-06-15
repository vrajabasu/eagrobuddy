package com.eagro.service.dto.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Threshold value for layout
 */
public enum OverallThresholdstateEnum {
	EXCEEDING_SOON("EXCEEDING_SOON"),

	EXCEEDED("EXCEEDED"),

	NORMAL("NORMAL");

	private String value;

	OverallThresholdstateEnum(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static OverallThresholdstateEnum fromValue(String text) {
		for (OverallThresholdstateEnum b : OverallThresholdstateEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return NORMAL;
	}
}
