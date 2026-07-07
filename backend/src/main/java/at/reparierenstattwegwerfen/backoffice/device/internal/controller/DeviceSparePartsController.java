package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSparePartDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSparePartService;
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
@RequestMapping("/api/devices/{deviceId}/spare-parts")
@RequiredArgsConstructor
public class DeviceSparePartsController {

	private final DeviceSparePartService deviceSparePartService;

	@GetMapping("/")
	public List<DeviceSparePartDto> getDeviceSpareParts(@PathVariable Integer deviceId) {
		return deviceSparePartService.load(deviceId);
	}
}