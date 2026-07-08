package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectsDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}")
@RequiredArgsConstructor
public class DeviceDefectsController {

	private final DeviceDefectDetailsService defectDetailsService;

	@GetMapping("/defects")
	public DeviceDefectsDetailsDto getDeviceDefectDetails(@PathVariable Integer deviceId) {
		return defectDetailsService.load(deviceId);
	}

	@PostMapping("/defects")
	public void updateDefects(@PathVariable Integer deviceId, @RequestBody DeviceDefectsDto deviceDefectsDto) {
		defectDetailsService.update(deviceId, deviceDefectsDto);
	}
}