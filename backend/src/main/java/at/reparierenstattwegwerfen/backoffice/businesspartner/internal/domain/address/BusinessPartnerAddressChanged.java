package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 19.09.2026
 */
@Getter
public class BusinessPartnerAddressChanged extends AbstractBusinessPartnerActivityEvent {

	private final Integer businessPartnerAddressId;

	@Builder
	public BusinessPartnerAddressChanged(Object source, UserDetails actor, Integer businessPartnerId,
										 Integer businessPartnerAddressId) {
		super(source, businessPartnerId, actor);
		this.businessPartnerAddressId = businessPartnerAddressId;
	}
}