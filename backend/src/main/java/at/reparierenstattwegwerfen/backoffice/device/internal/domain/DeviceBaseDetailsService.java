package at.reparierenstattwegwerfen.backoffice.device.internal.domain;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceBaseDetailsDto;
import at.reparierenstattwegwerfen.backoffice.model.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceBaseDetailsService {

	private final DeviceRepository deviceRepository;
	private final DeviceTagRepository deviceTagRepository;
	private final ModelDetailsService modelDetailsService;
	private final ModelAppleSiliconDetailsService modelAppleSiliconDetailsService;
	private final ModelUnifiedMemoryDetailsService modelUnifiedMemoryDetailsService;
	private final ModelColorDetailsService modelColorDetailsService;
	private final ModelStorageDetailsService modelStorageDetailsService;

	@Transactional(readOnly = true)
	public DeviceBaseDetailsDto load(Integer deviceId) {
		Device device = deviceRepository.getByIdWithRelations(deviceId);
		Integer deviceModelId = device.getModelId();

		return DeviceBaseDetailsDto.builder()
			.deviceId(deviceId)
			.status(NamedIdDto.from(device.getStatus()))
			.model(modelDetailsService.getModel(deviceModelId))
			.modelNumber(modelDetailsService.getModelNumber(deviceModelId))
			.grade(NamedIdDto.from(device.getGrade()))
			.technicalSpecsUrl(modelDetailsService.getTechnicalSpecsUrl(deviceModelId))
			.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
			.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
			.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
			.color(modelColorDetailsService.getColor(device.getModelColorId()))
			.serialNumber(device.getSerialNumber())
			.batteryMaximumCapacity(device.getBatteryMaximumCapacity())
			.batteryCycleCount(device.getBatteryCycleCount())
			.batteryStatus(NamedIdDto.from(device.getBatteryStatus()))
			.tags(deviceTagRepository.getTagsForDevice(deviceId).stream().map(NamedIdDto::from).toList())
			.build();
	}
}
