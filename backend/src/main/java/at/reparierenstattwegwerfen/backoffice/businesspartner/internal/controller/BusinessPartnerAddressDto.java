package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author Fabian Feichter
 */
@Value
@Builder
@EqualsAndHashCode
public class BusinessPartnerAddressDto {

	@EqualsAndHashCode.Include
	@NotNull
	Integer id;

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

	@NotBlank
	@Size(max = 256)
	String country;
}