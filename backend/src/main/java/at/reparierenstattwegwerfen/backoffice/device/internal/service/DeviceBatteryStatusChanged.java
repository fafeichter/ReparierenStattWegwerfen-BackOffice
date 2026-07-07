package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBatteryStatusChanged extends ApplicationEvent {

	final Integer deviceId;
	final Integer oldBatteryStatusId;
	final Integer newBatteryStatusId;

	@Builder
	public DeviceBatteryStatusChanged(Object source, Integer deviceId, Integer oldBatteryStatusId, Integer newBatteryStatusId) {
		super(source);
		this.deviceId = deviceId;
		this.oldBatteryStatusId = oldBatteryStatusId;
		this.newBatteryStatusId = newBatteryStatusId;
	}
}
