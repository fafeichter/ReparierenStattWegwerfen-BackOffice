package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceStatusChanged;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 18.09.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceArchivalService {

	private final DeviceRepository deviceRepository;
	private final ApplicationEventPublisher events;

	@Transactional
	public void archiveSoldDevices() {
		List<Integer> devicesToArchive = deviceRepository.findDevicesToArchive();

		if (!devicesToArchive.isEmpty()) {
			deviceRepository.archiveSoldDevices(devicesToArchive);

			UserDetails systemUser = SystemUser.get();
			devicesToArchive.forEach(deviceId -> {
				DeviceStatusChanged deviceStatusChangedEvent = DeviceStatusChanged.builder()
					.source(this)
					.actor(systemUser)
					.deviceId(deviceId)
					.newStatusId(8)
					.build();
				events.publishEvent(deviceStatusChangedEvent);
			});
		}
	}
}