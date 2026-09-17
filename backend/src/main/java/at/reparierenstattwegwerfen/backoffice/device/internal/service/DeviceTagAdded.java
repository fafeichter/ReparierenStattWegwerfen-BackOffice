package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceTagAdded extends DeviceActivityEvent {

	final Integer deviceId;
	final Integer newTagId;

	public DeviceTagAdded(Object source, UserDetails actor, Integer deviceId, Integer newTagId) {
		super(source, deviceId, actor);
		this.deviceId = deviceId;
		this.newTagId = newTagId;
	}
}