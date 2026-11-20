package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
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

	private final Double price;
	private final NamedIdDto buyer;
	private final LocalDate date;
}