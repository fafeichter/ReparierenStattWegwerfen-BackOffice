package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceTagAdded extends ApplicationEvent {

	final Integer deviceId;
	final Integer newTagId;

	public DeviceTagAdded(Object source, Integer deviceId, Integer newTagId) {
		super(source);
		this.deviceId = deviceId;
		this.newTagId = newTagId;
	}
}