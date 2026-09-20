package at.reparierenstattwegwerfen.backoffice.device;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBusinessPartnerSetAsBuyer extends AbstractDeviceActivityEvent {

	private final Integer buyerBusinessPartnerId;

	@Builder
	public DeviceBusinessPartnerSetAsBuyer(Object source, UserDetails actor, Integer deviceId, Integer buyerBusinessPartnerId) {
		super(source, deviceId, actor);
		this.buyerBusinessPartnerId = buyerBusinessPartnerId;
	}
}