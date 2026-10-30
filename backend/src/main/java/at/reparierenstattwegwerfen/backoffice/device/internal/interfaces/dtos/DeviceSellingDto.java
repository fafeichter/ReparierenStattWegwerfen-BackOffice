package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 30.10.2026
 */
@Data
public class DeviceSellingDto {

	@NotNull
	@Positive
	private Double sellingPrice;

	@NotNull
	@PastOrPresent
	private LocalDate sellingDate;
}