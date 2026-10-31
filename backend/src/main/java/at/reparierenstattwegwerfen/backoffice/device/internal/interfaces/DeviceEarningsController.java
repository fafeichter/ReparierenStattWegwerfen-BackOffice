package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.earnings.DeviceEarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 31.10.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/earnings")
@RequiredArgsConstructor
public class DeviceEarningsController {

	private final DeviceEarningsService deviceEarningsService;

	@GetMapping
	public Double getDeviceEarnings(@PathVariable Integer deviceId) {
		return deviceEarningsService.getDeviceEarnings(deviceId);
	}
}