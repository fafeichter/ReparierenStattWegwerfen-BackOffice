package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceGradeService {

	private final DeviceGradeRepository deviceGradeRepository;

	public List<DeviceGradeDto> getAllStatus() {
		return deviceGradeRepository.getAllStatus()
			.stream()
			.map(deviceStatus -> DeviceGradeDto.builder()
				.id(deviceStatus.getId())
				.name(deviceStatus.getName())
				.description(deviceStatus.getDescription())
				.build())
			.toList();
	}
}