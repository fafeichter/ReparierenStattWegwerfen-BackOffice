package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 20.09.2026
 */
@Getter
public class DeviceNoteDeleted extends AbstractDeviceActivityEvent {

	final Integer oldNoteId;

	@Builder
	public DeviceNoteDeleted(Object source, UserDetails actor, Integer deviceId, Integer oldNoteId) {
		super(source, deviceId, actor);
		this.oldNoteId = oldNoteId;
	}
}