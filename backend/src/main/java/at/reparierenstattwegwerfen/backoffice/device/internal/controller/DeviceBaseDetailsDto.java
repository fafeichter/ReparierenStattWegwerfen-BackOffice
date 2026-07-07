package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

	@Valid
	private final NamedIdDto appleSilicon;

	@Valid
	private final NamedIdDto unifiedMemory;

	@Valid
	private final NamedIdDto storage;

	@Valid
	private final NamedIdDto color;
	private final String serialNumber;
	private final Integer batteryMaximumCapacity;
	private final Integer batteryCycleCount;

	@Valid
	private final NamedIdDto batteryStatus;

	@Valid
	private final List<NamedIdDto> tags;
}
