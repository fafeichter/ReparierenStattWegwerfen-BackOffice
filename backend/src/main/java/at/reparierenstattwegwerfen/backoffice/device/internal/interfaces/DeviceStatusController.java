package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusService;
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
public class DeviceStatusController {

	private final DeviceStatusService deviceStatusService;

	@GetMapping("/status")
	public List<NamedIdDto> getAllNonSystemStatus() {
		return deviceStatusService.getAllNonSystemStatus();
	}

	@PostMapping("/{deviceId}/status")
	public void updateStatus(@PathVariable Integer deviceId, @RequestBody Integer newStatusId,
							 @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateStatusOfDevice(deviceId, newStatusId, currentUser);
	}
}