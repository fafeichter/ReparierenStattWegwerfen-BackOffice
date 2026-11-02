package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSparePartsService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.CreateDeviceSparePartDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSparePartDto;
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

	private final DeviceSparePartsService deviceSparePartsService;

	@GetMapping("/")
	public List<DeviceSparePartDto> getDeviceSpareParts(@PathVariable Integer deviceId) {
		return deviceSparePartsService.load(deviceId);
	}

	@GetMapping("/available")
	public List<NamedIdDto> getAvailableSparePartsForDevice(@PathVariable Integer deviceId) {
		return deviceSparePartsService.getAvailableSparePartsForDevice(deviceId);
	}

	@PostMapping("/")
	public void addDeviceSparePart(@PathVariable Integer deviceId, @Valid @RequestBody CreateDeviceSparePartDto newDeviceSparePartDto,
								   @AuthenticationPrincipal UserDetails currentUser) {
		deviceSparePartsService.addDeviceSparePart(deviceId, newDeviceSparePartDto, currentUser);
	}

	@DeleteMapping("/{deviceSparePartId}")
	public void deleteDeviceSparePart(@PathVariable Integer deviceId, @PathVariable Integer deviceSparePartId,
									  @AuthenticationPrincipal UserDetails currentUser) {
		deviceSparePartsService.deleteDeviceSparePart(deviceId, deviceSparePartId, currentUser);
	}
}