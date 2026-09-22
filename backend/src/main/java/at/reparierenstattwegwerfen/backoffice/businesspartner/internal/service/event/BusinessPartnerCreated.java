package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.businesspartner.AbstractBusinessPartnerActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 19.09.2026
 */
@Getter
public class BusinessPartnerCreated extends AbstractBusinessPartnerActivityEvent {

	@Builder
	public BusinessPartnerCreated(Object source, UserDetails actor, Integer businessPartnerId) {
		super(source, businessPartnerId, actor);
	}
}