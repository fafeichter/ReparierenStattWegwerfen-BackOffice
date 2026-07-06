package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceStatusChanged extends ApplicationEvent {

	final Integer deviceId;
	final Integer oldStatusId;
	final Integer newStatusId;

	@Builder
	public DeviceStatusChanged(Object source, Integer deviceId, Integer oldStatusId, Integer newStatusId) {
		super(source);
		this.deviceId = deviceId;
		this.oldStatusId = oldStatusId;
		this.newStatusId = newStatusId;
	}
}
