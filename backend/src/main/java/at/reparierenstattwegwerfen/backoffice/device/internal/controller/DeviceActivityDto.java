package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceActivityDto {

	@NotEmpty
	private final String name;
	private final String description;
	@NotNull
	private Integer id;
	@NotNull
	private LocalDateTime date;

}
