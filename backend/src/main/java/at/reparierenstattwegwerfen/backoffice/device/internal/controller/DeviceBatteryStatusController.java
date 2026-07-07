package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/devices/battery-status")
@RequiredArgsConstructor
public class DeviceBatteryStatusController {

	private final DeviceBatteryStatusService deviceBatteryStatusService;

	@GetMapping("/")
	public List<NamedIdDto> getAllBatteryStatus() {
		return deviceBatteryStatusService.getAllStatus();
	}
}