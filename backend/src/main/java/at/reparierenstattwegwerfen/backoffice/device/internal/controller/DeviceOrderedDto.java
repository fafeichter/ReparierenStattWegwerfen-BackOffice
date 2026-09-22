package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceOrderedDto {

	private final Integer deviceId;
	private final LocalDate buyingDate;
	private final NamedIdDto model;
	private final NamedIdDto appleSilicon;
	private final NamedIdDto unifiedMemory;
	private final NamedIdDto storage;
}