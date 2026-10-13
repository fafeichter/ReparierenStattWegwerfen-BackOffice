package at.reparierenstattwegwerfen.backoffice.device.internal.domain.defects;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceCorrectedDefectChanged extends AbstractDeviceActivityEvent {

	final String correctedDefect;

	@Builder
	public DeviceCorrectedDefectChanged(Object source, UserDetails actor, Integer deviceId, String correctedDefect) {
		super(source, deviceId, actor);
		this.correctedDefect = correctedDefect;
	}
}