package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBaseDetailsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceDefectDetailsService {

	private final DeviceBaseDetailsRepository deviceDetailsRepositoryRepository;
	private final DeviceRepository deviceRepository;

	@Transactional(readOnly = true)
	public DeviceDefectsDetailsDto load(Integer deviceId) {
		Device device = deviceDetailsRepositoryRepository.getByIdWithRelations(deviceId);

		return DeviceDefectsDetailsDto.builder()
			.reportedDefect(device.getReportedDefect()).diagnosedDefect(device.getDiagnosedDefect()).build();
	}

	@Transactional
	public void update(Integer deviceId, DeviceDefectsDto deviceDefectsDto) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setReportedDefect(deviceDefectsDto.getReportedDefect());
		device.setDiagnosedDefect(deviceDefectsDto.getDiagnosedDefect());
	}
}
