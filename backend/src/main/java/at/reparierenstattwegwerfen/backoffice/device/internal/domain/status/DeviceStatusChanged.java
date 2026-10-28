package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 02.07.2026
 */
@Getter
public class DeviceStatusChanged extends AbstractDeviceActivityEvent {

	final Integer newStatusId;

	DeviceStatusChanged(Object source, UserDetails actor, Integer deviceId, Integer newStatusId) {
		super(source, deviceId, actor);
		this.newStatusId = newStatusId;
	}

	@Builder
	public static DeviceStatusChanged of(Object source, UserDetails actor, Integer deviceId, Integer newStatusId) {
		if (Integer.valueOf(6).equals(newStatusId)) {
			return new DeviceSold(source, actor, deviceId);
		}

		return new DeviceStatusChanged(source, actor, deviceId, newStatusId);
	}
}