package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceBatteryHealthChanged extends AbstractDeviceActivityEvent {

	final Integer maximumCapacity;
	final Integer cycleCount;

	@Builder
	public DeviceBatteryHealthChanged(Object source, UserDetails actor, Integer deviceId, Integer maximumCapacity,
									  Integer cycleCount) {
		super(source, deviceId, actor);
		this.maximumCapacity = maximumCapacity;
		this.cycleCount = cycleCount;
	}
}