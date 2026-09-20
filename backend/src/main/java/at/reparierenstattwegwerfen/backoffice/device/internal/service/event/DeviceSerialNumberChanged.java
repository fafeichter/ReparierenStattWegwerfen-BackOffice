package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
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