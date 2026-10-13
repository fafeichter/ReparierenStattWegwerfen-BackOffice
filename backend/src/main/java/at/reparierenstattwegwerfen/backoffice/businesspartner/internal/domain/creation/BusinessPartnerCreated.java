package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.creation;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.AbstractBusinessPartnerActivityEvent;
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