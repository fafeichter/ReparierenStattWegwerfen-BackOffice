package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceDiagnosedDefectChanged extends AbstractDeviceActivityEvent {

	final String diagnosedDefect;

	@Builder
	public DeviceDiagnosedDefectChanged(Object source, UserDetails actor, Integer deviceId, String diagnosedDefect) {
		super(source, deviceId, actor);
		this.diagnosedDefect = diagnosedDefect;
	}
}