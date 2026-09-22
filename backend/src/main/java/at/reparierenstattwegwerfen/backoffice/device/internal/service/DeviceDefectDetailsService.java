package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBaseDetailsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.AbstractDeviceActivityEvent;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceDiagnosedDefectChanged;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceReportedDefectChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceDefectDetailsService {

	private final DeviceBaseDetailsRepository deviceDetailsRepositoryRepository;
	private final DeviceRepository deviceRepository;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public DeviceDefectsDetailsDto load(Integer deviceId) {
		Device device = deviceDetailsRepositoryRepository.getByIdWithRelations(deviceId);

		return DeviceDefectsDetailsDto.builder()
			.reportedDefect(device.getReportedDefect()).diagnosedDefect(device.getDiagnosedDefect()).build();
	}

	@Transactional
	public void update(Integer deviceId, DeviceDefectsDto deviceDefectsDto, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		boolean reportedDefectChanged = !deviceDefectsDto.getReportedDefect().equals(device.getReportedDefect());
		if (reportedDefectChanged) {
			device.setReportedDefect(deviceDefectsDto.getReportedDefect());
		}
		boolean diagnosedDefectChanged = !deviceDefectsDto.getDiagnosedDefect().equals(device.getDiagnosedDefect());
		if (diagnosedDefectChanged) {
			device.setDiagnosedDefect(deviceDefectsDto.getDiagnosedDefect());
		}

		if (reportedDefectChanged || diagnosedDefectChanged) {
			deviceRepository.save(device);

			List<AbstractDeviceActivityEvent> deviceEvents = new ArrayList<>();
			if (reportedDefectChanged) {
				DeviceReportedDefectChanged reportedDefectChangedEvent = DeviceReportedDefectChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.reportedDefect(deviceDefectsDto.getReportedDefect())
					.build();
				deviceEvents.add(reportedDefectChangedEvent);
			}

			if (diagnosedDefectChanged) {
				DeviceDiagnosedDefectChanged diagnosedDefectChangedEvent = DeviceDiagnosedDefectChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.diagnosedDefect(deviceDefectsDto.getDiagnosedDefect())
					.build();
				deviceEvents.add(diagnosedDefectChangedEvent);
			}

			deviceEvents.forEach(events::publishEvent);
		}
	}

	@Transactional
	public void confirmOriginalDefect(Integer deviceId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setDiagnosedDefect("WIE ANGEGEBEN");

		// todo: checkbox?
		deviceRepository.save(device);
	}
}