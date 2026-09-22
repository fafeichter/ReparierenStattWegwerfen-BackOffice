package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Data
@Builder
public class DeviceOfferedForSaleDto {

	private final Integer deviceId;
	private final LocalDate statusDate;
	private final NamedIdDto model;
	private final NamedIdDto appleSilicon;
	private final NamedIdDto unifiedMemory;
	private final NamedIdDto storage;
}