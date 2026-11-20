package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.AbstractBusinessPartnerActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.11.2026
 */
@Getter
public class BusinessPartnerUnmarkedAsScammer extends AbstractBusinessPartnerActivityEvent {

	@Builder
	public BusinessPartnerUnmarkedAsScammer(Object source, UserDetails actor, Integer businessPartnerId) {
		super(source, businessPartnerId, actor);
	}
}