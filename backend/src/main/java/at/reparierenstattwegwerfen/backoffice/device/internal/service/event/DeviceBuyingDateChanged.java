package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBuyingDateChanged extends AbstractDeviceActivityEvent {

	final LocalDate buyingDate;

	@Builder
	public DeviceBuyingDateChanged(Object source, UserDetails actor, Integer deviceId, LocalDate buyingDate) {
		super(source, deviceId, actor);
		this.buyingDate = buyingDate;
	}
}