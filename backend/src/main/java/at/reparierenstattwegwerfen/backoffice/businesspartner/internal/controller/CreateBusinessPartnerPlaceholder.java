package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class CreateBusinessPartnerPlaceholder {

	@NotEmpty
	@Size(max = 256)
	private final String firstName;

	@Size(max = 256)
	private final String lastName;
}