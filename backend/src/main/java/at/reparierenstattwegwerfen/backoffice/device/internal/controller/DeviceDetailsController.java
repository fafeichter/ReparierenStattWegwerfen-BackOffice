package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/{deviceId}")
@RequiredArgsConstructor
public class DeviceDetailsController {

	private final DeviceBaseDetailsService baseDetailsService;
	private final DeviceBuyingDetailsService buyingDetailsService;

	@GetMapping("/base")
	public DeviceBaseDetailsDto getDeviceBaseDetails(@PathVariable Integer deviceId) {
		return baseDetailsService.load(deviceId);
	}

	@GetMapping("/buying")
	public DeviceBuyingDetailsDto getDeviceBuyingDetails(@PathVariable Integer deviceId) {
		return buyingDetailsService.load(deviceId);
	}
}