package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceGradeDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceGradeService;
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
@RequestMapping("/api/devices/grades")
@RequiredArgsConstructor
public class DeviceGradeController {

	private final DeviceGradeService deviceGradeService;

	@GetMapping("/")
	public List<DeviceGradeDto> getAllGrades() {
		return deviceGradeService.getAllStatus();
	}
}