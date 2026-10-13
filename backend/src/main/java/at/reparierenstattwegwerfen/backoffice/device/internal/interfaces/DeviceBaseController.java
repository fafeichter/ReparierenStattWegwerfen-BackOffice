package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceBaseDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBaseDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 06.07.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/base")
@RequiredArgsConstructor
public class DeviceBaseController {

	private final DeviceBaseDetailsService baseDetailsService;

	@GetMapping("/")
	public DeviceBaseDetailsDto getDeviceBaseDetails(@PathVariable Integer deviceId) {
		return baseDetailsService.load(deviceId);
	}
}