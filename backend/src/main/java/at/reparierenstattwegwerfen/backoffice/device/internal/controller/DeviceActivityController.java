package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/activities")
@RequiredArgsConstructor
public class DeviceActivityController {

	private final DeviceActivityService deviceActivityService;

	@GetMapping("/")
	public List<DeviceActivityDto> getActivities(@PathVariable Integer deviceId) {
		return deviceActivityService.getForDevice(deviceId);
	}
}
