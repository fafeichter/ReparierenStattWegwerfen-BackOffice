package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 07.07.2026
 */
@Getter
public class DeviceBatteryStatusChanged extends AbstractDeviceActivityEvent {

	final Integer newBatteryStatusId;

	@Builder
	public DeviceBatteryStatusChanged(Object source, UserDetails actor, Integer deviceId, Integer newBatteryStatusId) {
		super(source, deviceId, actor);
		this.newBatteryStatusId = newBatteryStatusId;
	}
}
