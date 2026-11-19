package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 19.11.2026
 */
@Data
@Builder
public class DeviceLastActivityDto {

	private final Integer deviceId;
	private final String action;
	private final String actor;
	private LocalDateTime date;
}