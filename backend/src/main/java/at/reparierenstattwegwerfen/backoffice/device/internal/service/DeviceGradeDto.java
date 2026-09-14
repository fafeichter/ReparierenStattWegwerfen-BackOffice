package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceGradeDto {

	@NotNull
	private final Integer id;

	@NotEmpty
	private final String name;

	@NotEmpty
	private final String description;
}