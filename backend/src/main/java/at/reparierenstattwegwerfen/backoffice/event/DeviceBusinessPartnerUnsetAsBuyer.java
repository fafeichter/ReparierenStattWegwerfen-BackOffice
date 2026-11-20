package at.reparierenstattwegwerfen.backoffice.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.11.2026
 */
@Getter
public class DeviceBusinessPartnerUnsetAsBuyer extends PreciseTimestampedApplicationEvent {

	private final UserDetails actor;
	private final Integer deviceId;
	private final Integer oldBuyerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerUnsetAsBuyer(Object source, UserDetails actor, Integer deviceId,
											 Integer oldBuyerBusinessPartnerId) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
		this.oldBuyerBusinessPartnerId = oldBuyerBusinessPartnerId;
	}
}