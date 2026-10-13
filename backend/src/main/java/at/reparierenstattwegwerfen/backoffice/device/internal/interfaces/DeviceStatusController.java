package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices/status")
@RequiredArgsConstructor
public class DeviceStatusController {

	private final DeviceStatusService deviceStatusService;

	@GetMapping("/")
	public List<NamedIdDto> getAllNonSystemStatus() {
		return deviceStatusService.getAllNonSystemStatus();
	}
}