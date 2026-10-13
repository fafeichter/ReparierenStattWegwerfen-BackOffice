package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.hardware.DeviceHardwareService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.UpdateHardwareConfigDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/hardware")
@RequiredArgsConstructor
public class DeviceHardwareController {

	private final DeviceHardwareService deviceHardwareService;

	@PostMapping("/")
	public void updateHardwareConfig(@PathVariable Integer deviceId,
									 @Valid @RequestBody UpdateHardwareConfigDto updateHardwareConfigDto,
									 @AuthenticationPrincipal UserDetails currentUser) {
		deviceHardwareService.updateHardwareConfigOfDevice(deviceId, updateHardwareConfigDto, currentUser);
	}

	@PostMapping("/serial-number")
	public void updateSerialNumber(@PathVariable Integer deviceId, @RequestBody(required = false) String newSerialNumber,
								   @AuthenticationPrincipal UserDetails currentUser) {
		deviceHardwareService.updateSerialNumber(deviceId, newSerialNumber, currentUser);
	}
}