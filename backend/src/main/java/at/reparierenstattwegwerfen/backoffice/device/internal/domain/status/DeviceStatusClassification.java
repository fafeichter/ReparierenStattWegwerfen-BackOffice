package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Getter
@Setter
public class DeviceStatusClassification implements NamedId {

	private Integer id;
	private String name;
}