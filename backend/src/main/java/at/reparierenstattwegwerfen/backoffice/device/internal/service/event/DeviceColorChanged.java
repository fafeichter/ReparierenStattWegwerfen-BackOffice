package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceColorChanged extends AbstractDeviceActivityEvent {

	final Integer modelColorId;

	@Builder
	public DeviceColorChanged(Object source, UserDetails actor, Integer deviceId, Integer modelColorId) {
		super(source, deviceId, actor);
		this.modelColorId = modelColorId;
	}
}