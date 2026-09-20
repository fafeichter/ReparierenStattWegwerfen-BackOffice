package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
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