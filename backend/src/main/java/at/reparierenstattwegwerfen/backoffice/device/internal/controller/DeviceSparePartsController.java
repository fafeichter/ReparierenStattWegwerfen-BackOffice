package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSparePartDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSparePartService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.Valid;
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
@RequestMapping("/api/devices/{deviceId}/spare-parts")
@RequiredArgsConstructor
public class DeviceSparePartsController {

	private final DeviceSparePartService deviceSparePartService;

	@GetMapping("/")
	public List<DeviceSparePartDto> getDeviceSpareParts(@PathVariable Integer deviceId) {
		return deviceSparePartService.load(deviceId);
	}

	@GetMapping("/available")
	public List<NamedIdDto> getAvailableSparePartsForDevice(@PathVariable Integer deviceId) {
		return deviceSparePartService.getAvailableSparePartsForDevice(deviceId);
	}

	@PostMapping("/")
	public void addDeviceSparePart(@PathVariable Integer deviceId, @Valid @RequestBody CreateDeviceSparePartDto newDeviceSparePartDto,
								   @AuthenticationPrincipal UserDetails currentUser) {
		deviceSparePartService.addDeviceSparePart(deviceId, newDeviceSparePartDto, currentUser);
	}

	@DeleteMapping("/{deviceSparePartId}")
	public void deleteDeviceSparePart(@PathVariable Integer deviceId, @PathVariable Integer deviceSparePartId,
									  @AuthenticationPrincipal UserDetails currentUser) {
		deviceSparePartService.deleteDeviceSparePart(deviceId, deviceSparePartId, currentUser);
	}
}