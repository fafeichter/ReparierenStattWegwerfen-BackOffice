package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceBuyingDetailsDto {

	@NotNull
	@URL
	private final String url;

	@NotNull
	private final Double price;

	@NotNull
	private final NamedIdDto seller;

	@NotNull
	private final LocalDate date;
}