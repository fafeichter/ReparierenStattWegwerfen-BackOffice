package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceActivityDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceLastActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceActivityController {

	private final DeviceActivityService deviceActivityService;

	@GetMapping("/{deviceId}/activities")
	public List<DeviceActivityDto> getActivitiesForDevice(@PathVariable Integer deviceId) {
		return deviceActivityService.getActivitiesForDevice(deviceId);
	}

	@GetMapping("/activities")
	public List<DeviceLastActivityDto> getLastActivities(@PageableDefault(size = 5, direction = DESC) Pageable page) {
		return deviceActivityService.getLastActivities(page);
	}
}