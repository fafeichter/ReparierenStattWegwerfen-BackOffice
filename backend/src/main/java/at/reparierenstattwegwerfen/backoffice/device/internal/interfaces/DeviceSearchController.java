package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.search.DeviceSearchService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOfferedForSaleDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOrderedDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceToFinishDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Fabian Feichter
 * @since 28.09.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceSearchController {

	private final DeviceSearchService deviceSearchService;

	@GetMapping
	public Page<DeviceDto> search(@PageableDefault(direction = DESC) Pageable page,
								  @RequestParam(defaultValue = "false") Boolean includeInactiveDevices) {
		return deviceSearchService.search(page, includeInactiveDevices);
	}

	@GetMapping("/lookup")
	public List<Integer> findDeviceIdsBySerialNumber(@RequestParam String serialNumber) {
		return deviceSearchService.findDeviceIdsBySerialNumber(serialNumber);
	}

	@GetMapping("/to-finish")
	public List<DeviceToFinishDto> getDevicesToFinish() {
		return deviceSearchService.getDevicesToFinish();
	}

	@GetMapping("/offered-for-sale")
	public List<DeviceOfferedForSaleDto> getDevicesOfferedForSale() {
		return deviceSearchService.getDevicesOfferedForSale();
	}

	@GetMapping("/ordered")
	public List<DeviceOrderedDto> getDevicesOrdered() {
		return deviceSearchService.getDevicesOrdered();
	}
}