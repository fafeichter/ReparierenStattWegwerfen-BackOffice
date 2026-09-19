package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceTagAdded extends AbstractDeviceActivityEvent {

	final Integer newTagId;

	@Builder
	public DeviceTagAdded(Object source, UserDetails actor, Integer deviceId, Integer newTagId) {
		super(source, deviceId, actor);
		this.newTagId = newTagId;
	}
}