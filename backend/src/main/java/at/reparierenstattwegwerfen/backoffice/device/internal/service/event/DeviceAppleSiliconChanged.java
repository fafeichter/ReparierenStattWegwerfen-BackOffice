package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceAppleSiliconChanged extends AbstractDeviceActivityEvent {

	final Integer modelAppleSiliconId;

	@Builder
	public DeviceAppleSiliconChanged(Object source, UserDetails actor, Integer deviceId, Integer modelAppleSiliconId) {
		super(source, deviceId, actor);
		this.modelAppleSiliconId = modelAppleSiliconId;
	}
}