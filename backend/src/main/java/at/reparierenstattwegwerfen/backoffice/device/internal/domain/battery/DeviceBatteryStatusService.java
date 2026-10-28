package at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
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
public class DeviceBatteryStatusService {

	private final DeviceRepository deviceRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getAllStatus() {
		return deviceBatteryStatusRepository.getAllStatus()
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional
	public void updateBatteryStatus(Integer deviceId, Integer newBatteryStatusId, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		Integer currentBatteryStatusId = Optional.ofNullable(device.getBatteryStatus())
			.map(DeviceBatteryStatus::getId)
			.orElse(null);

		if (!Objects.equals(currentBatteryStatusId, newBatteryStatusId)) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getById(newBatteryStatusId));
			deviceRepository.save(device);

			DeviceBatteryStatusChanged deviceBatteryStatusChangedEvent = DeviceBatteryStatusChanged.builder()
				.source(this).actor(actor)
				.deviceId(deviceId)
				.newBatteryStatusId(newBatteryStatusId)
				.build();
			events.publishEvent(deviceBatteryStatusChangedEvent);
		}
	}
}