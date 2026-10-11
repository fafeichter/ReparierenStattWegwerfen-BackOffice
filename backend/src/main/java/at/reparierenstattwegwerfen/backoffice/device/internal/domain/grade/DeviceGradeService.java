package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceGradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 07.07.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceGradeService {

	private final DeviceGradeRepository deviceGradeRepository;

	@Transactional(readOnly = true)
	public List<DeviceGradeDto> getAllGrades() {
		return deviceGradeRepository.getAllGrades()
			.stream()
			.map(deviceStatus -> DeviceGradeDto.builder()
				.id(deviceStatus.getId())
				.name(deviceStatus.getName())
				.description(deviceStatus.getDescription())
				.build())
			.toList();
	}
}