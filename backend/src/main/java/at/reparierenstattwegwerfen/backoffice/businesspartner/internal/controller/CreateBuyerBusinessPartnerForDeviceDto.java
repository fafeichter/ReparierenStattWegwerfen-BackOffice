package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class CreateBuyerBusinessPartnerForDeviceDto {

	@NotEmpty
	@Size(max = 256)
	private final String firstName;

	@NotEmpty
	@Size(max = 256)
	private final String lastName;

	@NotNull
	Integer deviceId;

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