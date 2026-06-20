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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BusinessPartnerDto {

	@EqualsAndHashCode.Include
	@NotNull
	Integer id;

	@NotBlank
	@Size(max = 256)
	String name;

	@NotBlank
	@Size(max = 256)
	String firstName;

	@NotBlank
	@Size(max = 256)
	String lastName;

	@NotNull
	Boolean scammer;

	@NotNull
	BusinessPartnerAddressDto address;
}