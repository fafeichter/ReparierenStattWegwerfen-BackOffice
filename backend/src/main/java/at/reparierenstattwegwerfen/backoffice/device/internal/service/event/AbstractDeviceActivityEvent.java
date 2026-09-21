package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.event.PreciseTimestampedApplicationEvent;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public abstract class AbstractDeviceActivityEvent extends PreciseTimestampedApplicationEvent {

	final UserDetails actor;
	final Integer deviceId;

	public AbstractDeviceActivityEvent(Object source, Integer deviceId, UserDetails actor) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
	}
}