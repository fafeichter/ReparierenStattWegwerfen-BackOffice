package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceBusinesspartnerDto {

	private List<NamedIdDto> soldDevices;
	private List<NamedIdDto> boughtDevices;
}