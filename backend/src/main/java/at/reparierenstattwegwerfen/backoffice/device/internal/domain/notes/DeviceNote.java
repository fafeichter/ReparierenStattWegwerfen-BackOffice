package at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class DeviceNote implements NamedId {

	private Integer id;
	private Integer deviceId;
	private String text;
	private LocalDateTime date;

	@Override
	public String getName() {
		return text;
	}
}