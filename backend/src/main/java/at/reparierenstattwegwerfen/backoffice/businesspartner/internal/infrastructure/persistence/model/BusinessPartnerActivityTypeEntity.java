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
@Table(name = "business_partner_activity_type")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerActivityTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_partner_activity_type_id")
	private Integer id;

	@Column(name = "name")
	private String name;
}