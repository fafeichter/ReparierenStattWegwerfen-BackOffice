package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectsDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceDefectsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/defects")
@RequiredArgsConstructor
public class DeviceDefectsController {

	private final DeviceDefectDetailsService defectDetailsService;

	@GetMapping("/")
	public DeviceDefectsDetailsDto getDeviceDefectDetails(@PathVariable Integer deviceId) {
		return defectDetailsService.load(deviceId);
	}

	@PostMapping("/")
	public void updateDefects(@PathVariable Integer deviceId, @Valid @RequestBody DeviceDefectsDto deviceDefectsDto) {
		defectDetailsService.update(deviceId, deviceDefectsDto);
	}
}