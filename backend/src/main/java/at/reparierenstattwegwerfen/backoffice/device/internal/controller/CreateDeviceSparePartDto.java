package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 25.09.2026
 */
@Data
public class CreateDeviceSparePartDto {

	@NotNull
	private final Integer sparePartId;

	@NotNull
	@Positive
	private final Double priceNetto;
}