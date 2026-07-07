package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceTagRemoved extends ApplicationEvent {

	final Integer deviceId;
	final Integer tagId;

	public DeviceTagRemoved(Object source, Integer deviceId, Integer tagId) {
		super(source);
		this.deviceId = deviceId;
		this.tagId = tagId;
	}
}