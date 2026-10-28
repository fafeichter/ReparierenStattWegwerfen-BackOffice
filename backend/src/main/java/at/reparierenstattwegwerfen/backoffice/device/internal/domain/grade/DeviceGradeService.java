package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceGradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 07.07.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceGradeService {

	private final DeviceGradeRepository deviceGradeRepository;
	private final DeviceRepository deviceRepository;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public List<DeviceGradeDto> getAllGrades() {
		return deviceGradeRepository.getAllGrades()
			.stream()
			.map(deviceStatus -> DeviceGradeDto.builder()
				.id(deviceStatus.getId())
				.name(deviceStatus.getName())
				.build())
			.toList();
	}

	@Transactional
	public void updateGrade(Integer deviceId, Integer newGradeId, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		Integer currentGradeId = Optional.ofNullable(device.getGrade())
			.map(DeviceGrade::getId)
			.orElse(null);

		if (!Objects.equals(currentGradeId, newGradeId)) {
			device.setGrade(deviceGradeRepository.getById(newGradeId));
			deviceRepository.save(device);

			DeviceGradeChanged deviceGradeChangedEvent = DeviceGradeChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.newGradeId(newGradeId)
				.build();
			events.publishEvent(deviceGradeChangedEvent);
		}
	}
}