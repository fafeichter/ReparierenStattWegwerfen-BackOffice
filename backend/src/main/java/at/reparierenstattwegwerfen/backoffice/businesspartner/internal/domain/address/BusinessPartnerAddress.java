package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerAddress {
	private Integer id;
	private String street;
	private String houseNumber;
	private String zipCode;
	private String city;
	private BusinessPartnerAddressCountry country;

	@Override
	public String toString() {
		String streetVal = getValueOrDefault(street);
		String houseNumberVal = getValueOrDefault(houseNumber);
		String zipCodeVal = getValueOrDefault(zipCode);
		String cityVal = getValueOrDefault(city);

		String countryVal = "-";
		if (country != null) {
			countryVal = getValueOrDefault(country.getName());
		}

		return String.format(
			"%s %s, %s %s, %s",
			streetVal, houseNumberVal,
			zipCodeVal, cityVal,
			countryVal
		);
	}

	private String getValueOrDefault(String value) {
		return (value != null && !value.isBlank()) ? value.trim() : "-";
	}
}