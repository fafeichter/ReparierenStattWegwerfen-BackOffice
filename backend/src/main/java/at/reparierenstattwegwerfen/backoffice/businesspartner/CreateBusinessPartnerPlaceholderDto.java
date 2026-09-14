package at.reparierenstattwegwerfen.backoffice.businesspartner;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class CreateBusinessPartnerPlaceholderDto {

	@NotEmpty
	@Size(max = 256)
	private final String firstName;

	@Size(max = 256)
	private final String lastName;
}