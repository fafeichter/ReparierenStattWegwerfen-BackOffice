package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.creation.DeviceCreationService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.CreateNewDeviceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 11.10.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceCreationController {

	private final DeviceCreationService deviceCreationService;

	@PostMapping("/")
	public Integer createNewDevice(@Valid @RequestBody CreateNewDeviceDto newDevice,
								   @AuthenticationPrincipal UserDetails currentUser) {
		return deviceCreationService.createDevice(newDevice, currentUser);
	}
}