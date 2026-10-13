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
@Table(name = "business_partner_address_country")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerAddressCountryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_partner_address_country_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;

	@Column(name = "sort_order")
	private Integer sortOrder;
}