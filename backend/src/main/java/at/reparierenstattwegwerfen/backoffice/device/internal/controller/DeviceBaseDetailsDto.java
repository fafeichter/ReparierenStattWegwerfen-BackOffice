package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceBaseDetailsDto {

	@NotNull
	private final Integer deviceId;

	@NotNull
	private final NamedIdDto status;

	@NotNull
	private final NamedIdDto model;

	@NotNull
	private final String modelNumber;

	@NotNull
	private final NamedIdDto grade;

	@NotNull
	private final String technicalSpecsUrl;

	private final NamedIdDto appleSilicon;
	private final NamedIdDto unifiedMemory;
	private final NamedIdDto storage;
	private final NamedIdDto color;
	private final String serialNumber;
	private final Integer batteryMaximumCapacity;
	private final Integer batteryCycleCount;
	private final NamedIdDto batteryStatus;
}
