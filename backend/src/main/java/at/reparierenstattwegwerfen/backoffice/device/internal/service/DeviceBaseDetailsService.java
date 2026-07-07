package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBaseDetailsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceTagRepository;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceBaseDetailsService {

	private final DeviceBaseDetailsRepository deviceRepository;
	private final DeviceTagRepository deviceTagRepository;

	private final ModelDetailsService modelDetailsService;

	@Transactional(readOnly = true)
	public DeviceBaseDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);

		return DeviceBaseDetailsDto.builder()
			.deviceId(deviceId)
			.status(NamedIdDto.from(device.getStatus()))
			.model(modelDetailsService.getModel(device.getModelId()))
			.modelNumber(modelDetailsService.getModelNumber(device.getModelId()))
			.grade(NamedIdDto.from(device.getGrade()))
			.technicalSpecsUrl(modelDetailsService.getTechnicalSpecsUrl(deviceId))
			.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
			.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
			.storage(modelDetailsService.getStorage(device.getModelStorageId()))
			.color(modelDetailsService.getColor(device.getModelColorId()))
			.serialNumber(device.getSerialNumber())
			.batteryMaximumCapacity(device.getBatteryMaximumCapacity())
			.batteryCycleCount(device.getBatteryCycleCount())
			.batteryStatus(NamedIdDto.from(device.getBatteryStatus()))
			.tags(deviceTagRepository.getTagsForDevice(deviceId).stream().map(tag -> NamedIdDto.from(tag)).toList())
			.build();
	}
}
