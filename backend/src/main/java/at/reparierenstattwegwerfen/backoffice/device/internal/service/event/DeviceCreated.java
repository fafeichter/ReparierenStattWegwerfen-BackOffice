package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceCreated extends AbstractDeviceActivityEvent {

	@Builder
	public DeviceCreated(Object source, UserDetails actor, Integer deviceId) {
		super(source, deviceId, actor);
	}
}
