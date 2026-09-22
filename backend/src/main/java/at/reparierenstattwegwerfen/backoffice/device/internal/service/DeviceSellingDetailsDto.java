package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Data
@Builder
public class DeviceSellingDetailsDto {

	@NotNull
	private final Double price;

	@NotNull
	private final NamedIdDto buyer;

	@NotNull
	private final LocalDate date;
}