package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceReportedDefectChanged extends AbstractDeviceActivityEvent {

	final String reportedDefect;

	@Builder
	public DeviceReportedDefectChanged(Object source, UserDetails actor, Integer deviceId, String reportedDefect) {
		super(source, deviceId, actor);
		this.reportedDefect = reportedDefect;
	}
}