package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.CreateNewDeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceCreationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

	private final DeviceCreationService deviceCreationService;
	private final DeviceRepository deviceRepository;

	@GetMapping("/{deviceId}")
	public Device getDeviceDetails(@PathVariable Integer deviceId) {
		return deviceRepository.findById(deviceId).orElse(null);
	}

	@PostMapping("/")
	public Integer createNewDevice(@Valid @RequestBody CreateNewDeviceDto newDevice,
								   @AuthenticationPrincipal UserDetails currentUser) {
		return deviceCreationService.createDevice(newDevice, currentUser);
	}
}