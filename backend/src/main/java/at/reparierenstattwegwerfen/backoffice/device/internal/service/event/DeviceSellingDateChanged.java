package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceSellingDateChanged extends AbstractDeviceActivityEvent {

	final LocalDate sellingDate;

	@Builder
	public DeviceSellingDateChanged(Object source, UserDetails actor, Integer deviceId, LocalDate sellingDate) {
		super(source, deviceId, actor);
		this.sellingDate = sellingDate;
	}
}