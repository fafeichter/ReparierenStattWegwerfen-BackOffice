package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
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