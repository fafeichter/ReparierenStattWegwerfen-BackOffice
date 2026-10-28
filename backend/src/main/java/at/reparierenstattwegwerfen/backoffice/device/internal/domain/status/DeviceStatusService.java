package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 06.07.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceStatusService {

	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getAllNonSystemStatus() {
		return deviceStatusRepository.getAllNonSystemStatus()
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional
	public void updateStatusOfDevice(Integer deviceId, Integer newStatusId, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		if (!newStatusId.equals(device.getStatus().getId())) {
			device.setStatus(deviceStatusRepository.getById(newStatusId));
			deviceRepository.save(device);

			DeviceStatusChanged deviceStatusChangedEvent = DeviceStatusChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.newStatusId(newStatusId)
				.build();
			events.publishEvent(deviceStatusChangedEvent);
		}
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void setDeviceStatusDate(DeviceStatusChanged event) {
		Device device = deviceRepository.getById(event.getDeviceId());
		device.setStatusDate(event.getExactTimestamp());
		deviceRepository.save(device);
	}
}