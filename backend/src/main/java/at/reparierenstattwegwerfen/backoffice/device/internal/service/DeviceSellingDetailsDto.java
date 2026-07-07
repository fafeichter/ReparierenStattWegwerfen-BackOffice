package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
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