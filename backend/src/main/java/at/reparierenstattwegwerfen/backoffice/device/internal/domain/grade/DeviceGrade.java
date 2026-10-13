package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class DeviceGrade implements NamedId, Sortable {

	private Integer id;
	private String grade;
	private String description;
	private Integer sortOrder;

	@Override
	public String getName() {
		return grade + " - " + description;
	}
}