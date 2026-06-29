package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceDefectsDetailsDto {

	@NotNull
	private final String reportedDefect;

	@NotNull
	private final String diagnosedDefect;
}