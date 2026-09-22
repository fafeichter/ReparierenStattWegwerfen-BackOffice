package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
@Data
@Builder
public class DeviceBusinesspartnerDto {

	private List<NamedIdDto> soldDevices;
	private List<NamedIdDto> boughtDevices;
}