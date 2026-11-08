package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.AbstractBusinessPartnerActivityEvent;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerActivity implements NamedId {
	private Integer id;
	private String name;
	private String actor;
	private Integer businessPartnerId;
	private BusinessPartnerActivityType activityType;
	private LocalDateTime date;

	public BusinessPartnerActivity(LocalDateTime date, UserDetails actor) {
		this.date = date;
		this.actor = actor.getUsername();
	}

	public BusinessPartnerActivity(AbstractBusinessPartnerActivityEvent event) {
		this.date = event.getExactTimestamp();
		this.actor = event.getActor().getUsername();
	}
}