package at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 31.10.2026
 */
@Getter
public class DeviceNoteEdited extends AbstractDeviceActivityEvent {

	final Integer noteId;
	final String noteNewText;

	@Builder
	public DeviceNoteEdited(Object source, UserDetails actor, Integer deviceId, Integer noteId, String noteNewText) {
		super(source, deviceId, actor);
		this.noteId = noteId;
		this.noteNewText = noteNewText;
	}
}