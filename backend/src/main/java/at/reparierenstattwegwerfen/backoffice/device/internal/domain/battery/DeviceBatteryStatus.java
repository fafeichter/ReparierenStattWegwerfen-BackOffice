package at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery;

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
public class DeviceBatteryStatus implements NamedId, Sortable {

	private Integer id;
	private String name;
	private Integer sortOrder;
}