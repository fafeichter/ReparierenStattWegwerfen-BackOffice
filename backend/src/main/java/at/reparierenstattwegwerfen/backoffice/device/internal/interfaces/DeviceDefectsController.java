package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.defects.DeviceDefectDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDefectsDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDefectsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 08.07.2026
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
	public void updateDefects(@PathVariable Integer deviceId, @Valid @RequestBody DeviceDefectsDto deviceDefectsDto,
							  @AuthenticationPrincipal UserDetails currentUser) {
		defectDetailsService.update(deviceId, deviceDefectsDto, currentUser);
	}

	@PostMapping("/reported/confirm")
	public void confirmReportedDefect(@PathVariable Integer deviceId, @AuthenticationPrincipal UserDetails currentUser) {
		defectDetailsService.confirmReportedDefect(deviceId, currentUser);
	}
}