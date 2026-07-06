package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 */
public interface DeviceInspection {
	NamedIdDto getModel();

	NamedIdDto getModelColor();

	NamedIdDto getModelStorage();

	NamedIdDto getModelAppleSiliconUnifiedMemory();

	Integer getBatteryMaximumCapacity();

	Integer getBatteryCycleCount();

	String getSerialNumber();
}