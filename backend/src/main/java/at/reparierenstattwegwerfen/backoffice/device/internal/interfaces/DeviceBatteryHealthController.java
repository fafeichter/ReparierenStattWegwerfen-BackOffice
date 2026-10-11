package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryHealthService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.BatteryHealthDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/battery/health")
@RequiredArgsConstructor
public class DeviceBatteryHealthController {

	private final DeviceBatteryHealthService deviceBatteryHealthService;

	@PostMapping("/")
	public void updateBattery(@PathVariable Integer deviceId, @Valid @RequestBody BatteryHealthDto newDeviceBaseBattery,
							  @AuthenticationPrincipal UserDetails currentUser) {
		deviceBatteryHealthService.updateBatteryHealth(deviceId, newDeviceBaseBattery, currentUser);
	}
}