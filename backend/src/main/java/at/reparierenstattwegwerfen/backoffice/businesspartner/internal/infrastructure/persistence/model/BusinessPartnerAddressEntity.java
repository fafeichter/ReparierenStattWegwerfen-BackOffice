package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model;

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
public class BusinessPartnerAddressEntity {

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
	private BusinessPartnerAddressCountryEntity country;
}