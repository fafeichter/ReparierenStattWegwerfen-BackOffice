package at.reparierenstattwegwerfen.backoffice.businesspartner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fabian Feichter
 */
@Data
@NoArgsConstructor
public class CreateBusinessPartnerDto {

	@NotEmpty
	@Size(max = 256)
	String firstName;

	@NotEmpty
	@Size(max = 256)
	String lastName;

	@NotBlank
	@Size(max = 256)
	String street;

	@NotBlank
	@Size(max = 256)
	String houseNumber;

	@NotBlank
	@Size(max = 256)
	String zipCode;

	@NotBlank
	@Size(max = 256)
	String city;

	@NotNull
	Integer countryId;
}