package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceSerialNumberChanged extends AbstractDeviceActivityEvent {

	final String serialNumber;

	@Builder
	public DeviceSerialNumberChanged(Object source, UserDetails actor, Integer deviceId, String serialNumber) {
		super(source, deviceId, actor);
		this.serialNumber = serialNumber;
	}
}