package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
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
public class DeviceTags {

	private Integer id;
	private Device device;
	private DeviceTag deviceTag;
}