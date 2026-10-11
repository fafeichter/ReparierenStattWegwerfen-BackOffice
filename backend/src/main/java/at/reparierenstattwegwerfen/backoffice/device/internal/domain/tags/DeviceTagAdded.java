package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 02.07.2026
 */
@Getter
public class DeviceTagAdded extends AbstractDeviceActivityEvent {

	final Integer newDeviceTagId;

	@Builder
	public DeviceTagAdded(Object source, UserDetails actor, Integer deviceId, Integer newDeviceTagId) {
		super(source, deviceId, actor);
		this.newDeviceTagId = newDeviceTagId;
	}
}