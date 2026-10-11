package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGradeService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceGradeDto;
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
		return deviceGradeService.getAllGrades();
	}
}