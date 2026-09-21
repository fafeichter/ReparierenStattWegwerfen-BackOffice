package at.reparierenstattwegwerfen.backoffice.businesspartner;

import at.reparierenstattwegwerfen.backoffice.event.PreciseTimestampedApplicationEvent;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public abstract class AbstractBusinessPartnerActivityEvent extends PreciseTimestampedApplicationEvent {

	final UserDetails actor;
	final Integer businessPartnerId;

	public AbstractBusinessPartnerActivityEvent(Object source, Integer businessPartnerId, UserDetails actor) {
		super(source);
		this.actor = actor;
		this.businessPartnerId = businessPartnerId;
	}
}