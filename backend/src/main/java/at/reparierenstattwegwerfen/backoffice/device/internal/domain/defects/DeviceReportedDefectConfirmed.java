package at.reparierenstattwegwerfen.backoffice.device.internal.domain.defects;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Getter
public class DeviceReportedDefectConfirmed extends AbstractDeviceActivityEvent {

	private final String reportedDefect;

	@Builder
	public DeviceReportedDefectConfirmed(Object source, UserDetails actor, Integer deviceId, String reportedDefect) {
		super(source, deviceId, actor);
		this.reportedDefect = reportedDefect;
	}
}