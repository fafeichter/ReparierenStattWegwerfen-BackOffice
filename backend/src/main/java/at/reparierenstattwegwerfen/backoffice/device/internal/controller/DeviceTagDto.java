package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceTagDto {

	@NotNull
	private final Integer id;

	@NotEmpty
	private final String name;
}