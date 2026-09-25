package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 25.09.2026
 */
@Getter
public class DeviceSparePartAdded extends AbstractDeviceActivityEvent {

	final Integer deviceSparePartId;

	@Builder
	public DeviceSparePartAdded(Object source, UserDetails actor, Integer deviceId, Integer deviceSparePartId) {
		super(source, deviceId, actor);
		this.deviceSparePartId = deviceSparePartId;
	}
}