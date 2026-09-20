package at.reparierenstattwegwerfen.backoffice.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBusinessPartnerSetAsSeller extends ApplicationEvent {

	final UserDetails actor;
	final Integer deviceId;
	final Integer sellerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerSetAsSeller(Object source, UserDetails actor, Integer deviceId, Integer sellerBusinessPartnerId) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
		this.sellerBusinessPartnerId = sellerBusinessPartnerId;
	}
}