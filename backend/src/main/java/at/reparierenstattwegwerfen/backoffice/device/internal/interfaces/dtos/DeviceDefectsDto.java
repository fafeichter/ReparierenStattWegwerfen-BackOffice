package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Data
public class DeviceDefectsDto {

	@NotEmpty
	private final String reportedDefect;

	private final String diagnosedDefect;
}