package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceSparePartDto {

	@NotNull
	private final Integer sparePartId;

	@NotNull
	private final String name;

	@NotNull
	private final Double priceNetto;

	@NotNull
	private final LocalDateTime timestamp;
}