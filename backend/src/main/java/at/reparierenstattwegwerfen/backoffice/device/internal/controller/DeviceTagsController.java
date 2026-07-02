package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

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
@RequestMapping("/api/devices/{deviceId}/tags")
@RequiredArgsConstructor
public class DeviceTagsController {

	private final DeviceTagsService deviceTagsService;

	@GetMapping("/")
	public List<DeviceTagDto> getTagsForDevice(@PathVariable Integer deviceId) {
		return deviceTagsService.getTagsForDevice(deviceId);
	}
}
