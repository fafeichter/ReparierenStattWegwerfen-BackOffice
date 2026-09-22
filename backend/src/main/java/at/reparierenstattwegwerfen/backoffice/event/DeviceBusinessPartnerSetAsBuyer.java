package at.reparierenstattwegwerfen.backoffice.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceBusinessPartnerSetAsBuyer extends PreciseTimestampedApplicationEvent {

	final UserDetails actor;
	final Integer deviceId;
	final Integer buyerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerSetAsBuyer(Object source, UserDetails actor, Integer deviceId, Integer buyerBusinessPartnerId) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
		this.buyerBusinessPartnerId = buyerBusinessPartnerId;
	}
}