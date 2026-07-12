package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class BusinessPartnerAddressCountryDto {

	@EqualsAndHashCode.Include
	@NotNull
	Integer id;

	@NotEmpty
	@Size(max = 256)
	String name;

	@NotBlank
	@Size(max = 2)
	String code;
}