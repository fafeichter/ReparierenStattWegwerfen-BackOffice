package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/base")
@RequiredArgsConstructor
public class DeviceBaseController {

	private final DeviceBaseDetailsService baseDetailsService;
	private final DeviceStatusService deviceStatusService;

	@GetMapping("/")
	public DeviceBaseDetailsDto getDeviceBaseDetails(@PathVariable Integer deviceId) {
		return baseDetailsService.load(deviceId);
	}

	@PostMapping("/status")
	public void updateStatus(@PathVariable Integer deviceId, @RequestBody Integer newStatusId) {
		deviceStatusService.updateStatusOfDevice(deviceId, newStatusId);
	}
}