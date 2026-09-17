package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
abstract class DeviceActivityEvent extends ApplicationEvent {

	final UserDetails actor;
	final Integer deviceId;

	public DeviceActivityEvent(Object source, Integer deviceId, UserDetails actor) {
		super(source);
		this.actor = actor;
		this.deviceId = deviceId;
	}
}