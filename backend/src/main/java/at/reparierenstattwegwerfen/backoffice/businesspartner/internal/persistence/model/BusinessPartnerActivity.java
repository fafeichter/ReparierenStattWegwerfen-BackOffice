package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "business_partner_activity")
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerActivity implements NamedId {

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
	private BusinessPartner businessPartner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_partner_activity_type_id")
	private BusinessPartnerActivityType activityType;

	@Column(name = "date")
	private LocalDateTime date;

	public BusinessPartnerActivity(long date, UserDetails actor) {
		this.date = Instant.ofEpochMilli(date)
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
		this.actor = actor.getUsername();
	}
}