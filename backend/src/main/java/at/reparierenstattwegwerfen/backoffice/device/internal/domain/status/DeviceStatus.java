package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class DeviceStatus implements NamedId, Sortable {

	private Integer id;
	private String name;
	private Boolean isEndstatus;
	private Integer sortOrder;
	private DeviceStatusClassification deviceStatusClassification;
}