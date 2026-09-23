package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Data
@Builder
public class DeviceSparePartDto {

	@NotNull
	private final Integer deviceSparePartId;

	@NotNull
	private final String name;

	@NotNull
	private final Double priceNetto;

	@NotNull
	private final LocalDateTime date;
}