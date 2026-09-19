package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

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
public class BusinessPartnerActivityDto {

	@NotEmpty
	private final String action;

	@NotEmpty
	private final String value;

	@NotEmpty
	private final String actor;

	@NotNull
	private Integer id;

	@NotNull
	private LocalDateTime date;
}