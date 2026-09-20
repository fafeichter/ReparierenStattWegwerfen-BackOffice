package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceGradeChanged extends AbstractDeviceActivityEvent {

	final Integer newGradeId;

	@Builder
	public DeviceGradeChanged(Object source, UserDetails actor, Integer deviceId, Integer newGradeId) {
		super(source, deviceId, actor);
		this.newGradeId = newGradeId;
	}
}