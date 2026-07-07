package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class BatteryStatusAutomaticallySet extends ApplicationEvent {

	final Integer deviceId;
	final Integer batteryStatusId;

	@Builder
	public BatteryStatusAutomaticallySet(Object source, Integer deviceId, Integer batteryStatusId) {
		super(source);
		this.deviceId = deviceId;
		this.batteryStatusId = batteryStatusId;
	}
}