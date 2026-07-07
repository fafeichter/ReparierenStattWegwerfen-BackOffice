package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceGradeRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
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

	public List<NamedIdDto> getAllStatus() {
		return deviceGradeRepository.getAllStatus()
			.stream()
			.map(deviceStatus -> NamedIdDto.from(deviceStatus))
			.toList();
	}
}