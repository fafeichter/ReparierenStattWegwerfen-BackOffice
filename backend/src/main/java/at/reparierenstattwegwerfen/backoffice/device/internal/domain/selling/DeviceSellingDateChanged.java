package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
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