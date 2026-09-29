package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 28.09.2026
 */
@Data
@Builder
public class DeviceDto {

	private Integer deviceId;
	private NamedIdDto status;
	private NamedIdDto model;
	private NamedIdDto appleSilicon;
	private NamedIdDto unifiedMemory;
	private NamedIdDto storage;
	private LocalDateTime lastActivity;
}