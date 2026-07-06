package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.DeviceStatusChanged;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceStatusService {

	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final ApplicationEventPublisher events;

	public List<NamedIdDto> getAllStatus() {
		return deviceStatusRepository.getAllStatus()
			.stream()
			.map(deviceStatus -> NamedIdDto.from(deviceStatus))
			.toList();
	}

	@Transactional
	public void updateStatusOfDevice(Integer deviceId, Integer newStatusId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceStatusChanged deviceStatusChanged = new DeviceStatusChanged(
			this, device.getId(), device.getStatus().getId(), newStatusId);
		device.setStatus(deviceStatusRepository.getReferenceById(newStatusId));

		deviceRepository.save(device);
		events.publishEvent(deviceStatusChanged);
	}
}