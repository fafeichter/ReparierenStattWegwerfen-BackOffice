package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBaseDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceDefectDetailsService {

	private final DeviceBaseDetailsRepository deviceRepository;

	@Transactional(readOnly = true)
	public DeviceDefectsDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);

		return DeviceDefectsDetailsDto.builder()
			.reportedDefect(device.getReportedDefect()).diagnosedDefect(device.getDiagnosedDefect()).build();
	}
}
