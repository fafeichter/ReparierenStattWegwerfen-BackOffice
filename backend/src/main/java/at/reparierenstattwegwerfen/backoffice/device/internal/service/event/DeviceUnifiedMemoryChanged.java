package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceUnifiedMemoryChanged extends AbstractDeviceActivityEvent {

	final Integer modelUnifiedMemoryId;

	@Builder
	public DeviceUnifiedMemoryChanged(Object source, UserDetails actor, Integer deviceId, Integer modelUnifiedMemoryId) {
		super(source, deviceId, actor);
		this.modelUnifiedMemoryId = modelUnifiedMemoryId;
	}
}