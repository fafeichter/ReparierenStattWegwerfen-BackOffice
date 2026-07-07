package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceNoteDto {

	@NotNull
	private final Integer noteId;

	@NotNull
	private final String text;

	@NotNull
	private final LocalDateTime date;
}
