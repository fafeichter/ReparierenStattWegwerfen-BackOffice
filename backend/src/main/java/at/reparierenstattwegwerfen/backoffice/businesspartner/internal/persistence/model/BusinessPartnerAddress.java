package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "business_partner_address")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_partner_address_id")
	private Integer id;

	@Column(name = "street")
	private String street;

	@Column(name = "house_number")
	private String houseNumber;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "city")
	private String city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_partner_address_country_id")
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
			"%s %s%n%s %s%n%s",
			streetVal, houseNumberVal,
			zipCodeVal, cityVal,
			countryVal
		);
	}

	private String getValueOrDefault(String value) {
		return (value != null && !value.isBlank()) ? value.trim() : "-";
	}
}