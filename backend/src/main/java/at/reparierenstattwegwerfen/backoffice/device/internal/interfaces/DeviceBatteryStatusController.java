package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatusService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceBatteryStatusController {

	private final DeviceBatteryStatusService deviceBatteryStatusService;

	@GetMapping("/battery/status")
	public List<NamedIdDto> getAllBatteryStatus() {
		return deviceBatteryStatusService.getAllStatus();
	}


	// todo
	@PostMapping("/{deviceId}/battery/status")
	public void updateBatteryStatus(@PathVariable Integer deviceId, @RequestBody Integer newBatteryStatusId,
									@AuthenticationPrincipal UserDetails currentUser) {
		deviceBatteryStatusService.updateBatteryStatus(deviceId, newBatteryStatusId, currentUser);
	}
}