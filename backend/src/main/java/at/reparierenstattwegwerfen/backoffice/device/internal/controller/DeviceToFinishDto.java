package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceToFinishDto {

	private final Integer deviceId;
	private final NamedIdDto status;
	private final NamedIdDto model;
	private final NamedIdDto appleSilicon;
	private final NamedIdDto unifiedMemory;
	private final NamedIdDto storage;
}