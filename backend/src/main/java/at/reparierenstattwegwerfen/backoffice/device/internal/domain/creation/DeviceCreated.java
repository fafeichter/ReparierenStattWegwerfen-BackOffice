package at.reparierenstattwegwerfen.backoffice.device.internal.domain.creation;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 02.07.2026
 */
@Getter
public class DeviceCreated extends AbstractDeviceActivityEvent {

	@Builder
	public DeviceCreated(Object source, UserDetails actor, Integer deviceId) {
		super(source, deviceId, actor);
	}
}
