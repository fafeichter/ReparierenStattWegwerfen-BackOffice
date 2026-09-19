package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceTagRemoved extends AbstractDeviceActivityEvent {

	final Integer oldTagId;

	@Builder
	public DeviceTagRemoved(Object source, UserDetails actor, Integer deviceId, Integer oldTagId) {
		super(source, deviceId, actor);
		this.oldTagId = oldTagId;
	}
}