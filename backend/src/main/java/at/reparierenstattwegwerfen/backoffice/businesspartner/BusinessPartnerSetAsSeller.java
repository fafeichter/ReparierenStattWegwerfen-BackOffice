package at.reparierenstattwegwerfen.backoffice.businesspartner;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class BusinessPartnerSetAsSeller extends AbstractBusinessPartnerActivityEvent {

	private final Integer deviceId;

	@Builder
	public BusinessPartnerSetAsSeller(Object source, UserDetails actor, Integer businessPartnerId, Integer deviceId) {
		super(source, businessPartnerId, actor);
		this.deviceId = deviceId;
	}
}