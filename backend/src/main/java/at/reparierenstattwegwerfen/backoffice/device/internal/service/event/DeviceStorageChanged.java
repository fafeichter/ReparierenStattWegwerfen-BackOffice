package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceStorageChanged extends AbstractDeviceActivityEvent {

	final Integer modelStorageId;

	@Builder
	public DeviceStorageChanged(Object source, UserDetails actor, Integer deviceId, Integer modelStorageId) {
		super(source, deviceId, actor);
		this.modelStorageId = modelStorageId;
	}
}