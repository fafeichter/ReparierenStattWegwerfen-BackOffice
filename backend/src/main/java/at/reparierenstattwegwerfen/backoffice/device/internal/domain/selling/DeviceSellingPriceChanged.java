package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 30.10.2026
 */
@Getter
public class DeviceSellingPriceChanged extends AbstractDeviceActivityEvent {

	final Double sellingPrice;

	@Builder
	public DeviceSellingPriceChanged(Object source, UserDetails actor, Integer deviceId, Double sellingPrice) {
		super(source, deviceId, actor);
		this.sellingPrice = sellingPrice;
	}
}