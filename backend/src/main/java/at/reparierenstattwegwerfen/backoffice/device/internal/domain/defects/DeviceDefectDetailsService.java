package at.reparierenstattwegwerfen.backoffice.device.internal.domain.defects;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDefectsDetailsDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDefectsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceDefectDetailsService {

	private final DeviceRepository deviceRepository;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public DeviceDefectsDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);

		return DeviceDefectsDetailsDto.builder()
			.reportedDefect(device.getReportedDefect())
			.reportedDefectConfirmed(device.getReportedDefectConfirmed())
			.correctedDefect(device.getCorrectedDefect())
			.build();
	}

	@Transactional
	public void update(Integer deviceId, DeviceDefectsDto deviceDefectsDto, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		boolean reportedDefectChanged = !deviceDefectsDto.getReportedDefect().equals(device.getReportedDefect());
		if (reportedDefectChanged) {
			device.setReportedDefect(deviceDefectsDto.getReportedDefect());
		}
		final String correctedDefect = deviceDefectsDto.getCorrectedDefect();
		boolean correctedDefectChanged = !Objects.equals(correctedDefect, device.getCorrectedDefect());
		Boolean reportedDefectConfirmedOld = device.getReportedDefectConfirmed();
		if (correctedDefectChanged) {
			device.setCorrectedDefect(correctedDefect);
			device.setReportedDefectConfirmed(false);
		}

		if (reportedDefectChanged || correctedDefectChanged) {
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

			if (correctedDefectChanged) {
				if (reportedDefectConfirmedOld == null || reportedDefectConfirmedOld) {
					DeviceReportedDefectNotConfirmed reportedDefectNotConfirmedEvent = DeviceReportedDefectNotConfirmed.builder()
						.source(this)
						.actor(actor)
						.deviceId(deviceId)
						.reportedDefect(device.getReportedDefect())
						.correctedDefect(deviceDefectsDto.getCorrectedDefect())
						.build();
					deviceEvents.add(reportedDefectNotConfirmedEvent);
				}
				if (StringUtils.hasText(correctedDefect)) {
					DeviceCorrectedDefectChanged correctedDefectChangedEvent = DeviceCorrectedDefectChanged.builder()
						.source(this)
						.actor(actor)
						.deviceId(deviceId)
						.correctedDefect(correctedDefect)
						.build();
					deviceEvents.add(correctedDefectChangedEvent);
				} else {
					confirmReportedDefect(deviceId, actor);
				}
			}

			deviceEvents.forEach(events::publishEvent);
		}
	}

	@Transactional
	public void confirmReportedDefect(Integer deviceId, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);
		device.setReportedDefectConfirmed(true);
		device.setCorrectedDefect(null);

		deviceRepository.save(device);

		DeviceReportedDefectConfirmed reportedDefectConfirmedEvent = DeviceReportedDefectConfirmed.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.reportedDefect(device.getReportedDefect())
			.build();
		events.publishEvent(reportedDefectConfirmedEvent);
	}
}