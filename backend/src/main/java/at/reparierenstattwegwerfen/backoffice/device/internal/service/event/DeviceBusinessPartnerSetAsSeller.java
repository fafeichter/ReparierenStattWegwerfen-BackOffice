package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBusinessPartnerSetAsSeller extends AbstractDeviceActivityEvent {

	final Integer sellerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerSetAsSeller(Object source, UserDetails actor, Integer deviceId,
											Integer sellerBusinessPartnerId) {
		super(source, deviceId, actor);
		this.sellerBusinessPartnerId = sellerBusinessPartnerId;
	}
}