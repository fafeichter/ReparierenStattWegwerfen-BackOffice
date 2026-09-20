package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceStatusChanged extends AbstractDeviceActivityEvent {

	final Integer newStatusId;

	@Builder
	public DeviceStatusChanged(Object source, UserDetails actor, Integer deviceId, Integer newStatusId) {
		super(source, deviceId, actor);
		this.newStatusId = newStatusId;
	}
}