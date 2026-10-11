package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.search.DeviceSearchService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOrderedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

	private final DeviceRepository deviceRepository;
	private final DeviceSearchService deviceService;

	@GetMapping("/{deviceId}")
	public Device getDeviceDetails(@PathVariable Integer deviceId) {
		return deviceRepository.findById(deviceId).orElse(null);
	}

	@GetMapping("/ordered")
	public List<DeviceOrderedDto> getDevicesOrdered() {
		return deviceService.getDevicesOrdered();
	}
}