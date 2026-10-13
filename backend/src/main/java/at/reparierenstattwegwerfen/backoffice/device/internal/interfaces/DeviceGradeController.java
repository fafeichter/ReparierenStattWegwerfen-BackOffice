package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGradeService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceGradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceGradeController {

	private final DeviceGradeService deviceGradeService;

	@GetMapping("/grades")
	public List<DeviceGradeDto> getAllGrades() {
		return deviceGradeService.getAllGrades();
	}

	@PostMapping("/{deviceId}/grade")
	public void updateGrade(@PathVariable Integer deviceId, @RequestBody Integer newGradeId,
							@AuthenticationPrincipal UserDetails currentUser) {
		deviceGradeService.updateGrade(deviceId, newGradeId, currentUser);
	}
}