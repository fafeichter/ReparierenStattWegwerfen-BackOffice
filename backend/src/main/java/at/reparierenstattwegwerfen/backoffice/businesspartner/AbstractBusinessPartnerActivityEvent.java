package at.reparierenstattwegwerfen.backoffice.businesspartner;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public abstract class AbstractBusinessPartnerActivityEvent extends ApplicationEvent {

	final UserDetails actor;
	final Integer businessPartnerId;

	public AbstractBusinessPartnerActivityEvent(Object source, Integer businessPartnerId, UserDetails actor) {
		super(source);
		this.actor = actor;
		this.businessPartnerId = businessPartnerId;
	}
}