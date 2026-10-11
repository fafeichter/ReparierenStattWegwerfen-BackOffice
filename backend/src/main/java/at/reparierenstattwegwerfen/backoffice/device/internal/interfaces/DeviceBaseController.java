package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceBaseDetailsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBaseDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.UpdateHardwareConfigDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 06.07.2026
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

	@PostMapping("/hardware-config")
	public void updateHardwareConfig(@PathVariable Integer deviceId,
									 @Valid @RequestBody UpdateHardwareConfigDto updateHardwareConfigDto,
									 @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateHardwareConfigOfDevice(deviceId, updateHardwareConfigDto, currentUser);
	}

	@PostMapping("/status")
	public void updateStatus(@PathVariable Integer deviceId, @RequestBody Integer newStatusId,
							 @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateStatusOfDevice(deviceId, newStatusId, currentUser);
	}

	@PostMapping("/serial-number")
	public void updateSerialNumber(@PathVariable Integer deviceId, @RequestBody(required = false) String newSerialNumber,
								   @AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateSerialNumber(deviceId, newSerialNumber, currentUser);
	}

	@PostMapping("/grade")
	public void updateGrade(@PathVariable Integer deviceId, @RequestBody Integer newGradeId,
							@AuthenticationPrincipal UserDetails currentUser) {
		deviceStatusService.updateGrade(deviceId, newGradeId, currentUser);
	}
}