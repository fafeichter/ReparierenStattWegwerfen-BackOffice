package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "business_partner_activity")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerActivityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_partner_activity_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "actor")
	private String actor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_partner_id")
	private BusinessPartnerEntity businessPartner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_partner_activity_type_id")
	private BusinessPartnerActivityTypeEntity activityType;

	@Column(name = "date")
	private LocalDateTime date;
}