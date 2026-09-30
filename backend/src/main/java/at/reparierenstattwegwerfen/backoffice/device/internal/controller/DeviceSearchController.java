package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 28.09.2026
 */
@RestController
@RequestMapping("/api/devices/search")
@RequiredArgsConstructor
public class DeviceSearchController {

	private final DeviceService deviceService;

	@GetMapping("/")
	public Page<DeviceDto> search(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
								  @RequestParam Integer pageSize,
								  @RequestParam(defaultValue = "false") Boolean includeInactiveDevices) {
		return deviceService.search(pageNumber, pageSize, includeInactiveDevices);
	}
}