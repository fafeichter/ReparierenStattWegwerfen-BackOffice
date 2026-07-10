package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * @author Fabian Feichter
 */
@Value
@RequiredArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode
public class BusinessPartnerAddressExtractResponse {

	@JsonProperty(required = true, value = "first_name")
	@JsonPropertyDescription("First name.")
	String firstName;

	@JsonProperty(required = true, value = "last_name")
	@JsonPropertyDescription("Last name.")
	String lastName;

	@JsonProperty(required = true, value = "street")
	@JsonPropertyDescription("Street name.")
	String street;

	@JsonProperty(required = true, value = "house_number")
	@JsonPropertyDescription("House number.")
	String houseNumber;

	@JsonProperty(required = true, value = "zip_code")
	@JsonPropertyDescription("ZIP code.")
	String zipCode;

	@JsonProperty(required = true, value = "city")
	@JsonPropertyDescription("City name.")
	String city;

	@JsonProperty(required = true, value = "country_code")
	@JsonPropertyDescription("Country code.")
	String countryCode;
}