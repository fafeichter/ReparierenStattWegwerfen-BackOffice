package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceGradeChanged extends DeviceActivityEvent {

	final Integer deviceId;
	final Integer newGradeId;

	@Builder
	public DeviceGradeChanged(Object source, UserDetails actor, Integer deviceId, Integer newGradeId) {
		super(source, deviceId, actor);
		this.deviceId = deviceId;
		this.newGradeId = newGradeId;
	}
}