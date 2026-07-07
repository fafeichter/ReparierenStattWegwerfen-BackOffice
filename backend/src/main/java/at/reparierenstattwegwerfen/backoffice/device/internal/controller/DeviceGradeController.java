package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceGradeService;
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
@RequestMapping("/api/devices/grades")
@RequiredArgsConstructor
public class DeviceGradeController {

	private final DeviceGradeService deviceGradeService;

	@GetMapping("/")
	public List<NamedIdDto> getAllGrades() {
		return deviceGradeService.getAllStatus();
	}
}