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
}