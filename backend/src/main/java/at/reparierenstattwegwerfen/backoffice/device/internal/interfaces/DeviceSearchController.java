package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.search.DeviceSearchService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDto;
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

	private final DeviceSearchService deviceService;

	@GetMapping("/")
	public Page<DeviceDto> search(@RequestParam(defaultValue = "1", required = false) Integer pageNumber,
								  @RequestParam Integer pageSize,
								  @RequestParam(defaultValue = "false") Boolean includeInactiveDevices) {
		return deviceService.search(pageNumber, pageSize, includeInactiveDevices);
	}
}