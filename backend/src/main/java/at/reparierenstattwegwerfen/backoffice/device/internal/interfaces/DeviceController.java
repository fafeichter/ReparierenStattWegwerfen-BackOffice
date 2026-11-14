package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceBaseDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBaseDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

	private final DeviceBaseDetailsService deviceDetailsService;

	@GetMapping("/{deviceId}")
	public DeviceBaseDetailsDto getDeviceBaseDetails(@PathVariable Integer deviceId) {
		return deviceDetailsService.load(deviceId);
	}

	@GetMapping("/")
	public List<Integer> getDeviceIdsWithUrl(@RequestParam String deviceUrl) {
		return deviceDetailsService.getDeviceIdsWithUrl(deviceUrl);
	}
}