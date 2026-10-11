package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling.DeviceSellingAccessoriesService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesFormDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
 * @since 08.07.2026
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/selling-accessories")
@RequiredArgsConstructor
public class DeviceSellingAccessoriesController {

	private final DeviceSellingAccessoriesService sellingAccessoriesService;

	@GetMapping("/")
	public DeviceSellingAccessoriesDto getSellingAccessories(@PathVariable Integer deviceId) {
		return sellingAccessoriesService.load(deviceId);
	}

	@PostMapping("/")
	public void updateSellingAccessories(@PathVariable Integer deviceId, @Valid @RequestBody DeviceSellingAccessoriesFormDto accessoriesFormDto) {
		sellingAccessoriesService.update(deviceId, accessoriesFormDto);
	}
}