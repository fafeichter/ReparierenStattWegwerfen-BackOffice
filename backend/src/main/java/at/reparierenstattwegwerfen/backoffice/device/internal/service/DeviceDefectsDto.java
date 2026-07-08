package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class DeviceDefectsDto {

	@NotEmpty
	private final String reportedDefect;

	private final String diagnosedDefect;
}