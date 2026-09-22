package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Data
@Builder
public class DeviceTagDto {

	@NotNull
	private final Integer id;

	@NotEmpty
	private final String name;
}