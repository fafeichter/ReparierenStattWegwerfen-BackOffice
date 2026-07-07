package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.Valid;
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

	@PostMapping("/serial-number")
	public void updateSerialNumber(@PathVariable Integer deviceId, @RequestBody(required = false) String newSerialNumber) {
		deviceStatusService.updateSerialNumber(deviceId, newSerialNumber);
	}

	@PostMapping("/battery")
	public void updateBattery(@PathVariable Integer deviceId, @RequestBody @Valid BatteryHealth newDeviceBaseBattery) {
		deviceStatusService.updateBattery(deviceId, newDeviceBaseBattery);
	}

	@PostMapping("/battery-status")
	public void updateBatteryStatus(@PathVariable Integer deviceId, @RequestBody Integer newBatteryStatusId) {
		deviceStatusService.updateBatteryStatus(deviceId, newBatteryStatusId);
	}
}