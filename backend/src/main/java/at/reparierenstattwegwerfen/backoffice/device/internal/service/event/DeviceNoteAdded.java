package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
@Getter
public class DeviceNoteAdded extends AbstractDeviceActivityEvent {

	final Integer newNoteId;

	@Builder
	public DeviceNoteAdded(Object source, UserDetails actor, Integer deviceId, Integer newNoteId) {
		super(source, deviceId, actor);
		this.newNoteId = newNoteId;
	}
}