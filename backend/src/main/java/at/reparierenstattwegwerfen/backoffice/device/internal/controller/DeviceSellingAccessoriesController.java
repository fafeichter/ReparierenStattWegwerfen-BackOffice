package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSellingAccessoriesDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSellingAccessoriesFormDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceSellingAccessoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fabian Feichter
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