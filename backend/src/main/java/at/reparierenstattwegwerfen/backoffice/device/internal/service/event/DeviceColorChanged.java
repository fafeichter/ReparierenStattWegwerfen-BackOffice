package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
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