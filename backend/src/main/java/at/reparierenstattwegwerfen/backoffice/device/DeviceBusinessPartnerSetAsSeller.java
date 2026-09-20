package at.reparierenstattwegwerfen.backoffice.device;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBusinessPartnerSetAsSeller extends AbstractDeviceActivityEvent {

	private final Integer sellerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerSetAsSeller(Object source, UserDetails actor, Integer deviceId, Integer sellerBusinessPartnerId) {
		super(source, deviceId, actor);
		this.sellerBusinessPartnerId = sellerBusinessPartnerId;
	}
}