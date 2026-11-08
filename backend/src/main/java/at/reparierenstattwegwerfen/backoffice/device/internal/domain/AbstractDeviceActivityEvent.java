package at.reparierenstattwegwerfen.backoffice.device.internal.domain;

import at.reparierenstattwegwerfen.backoffice.event.PreciseTimestampedApplicationEvent;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 02.07.2026
 */
@Getter
public abstract class AbstractDeviceActivityEvent extends PreciseTimestampedApplicationEvent {

	private final UserDetails actor;
	private final Integer deviceId;

	public AbstractDeviceActivityEvent(Object source, Integer deviceId, UserDetails actor) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
	}
}