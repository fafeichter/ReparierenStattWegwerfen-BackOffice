package at.reparierenstattwegwerfen.backoffice.device.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.UpdateHardwareConfigDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceHardwareService {

	private final DeviceRepository deviceRepository;
	private final ApplicationEventPublisher events;

	@Transactional
	public void updateSerialNumber(Integer deviceId, String newSerialNumber, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		if (!Objects.equals(newSerialNumber, device.getSerialNumber())) {
			device.setSerialNumber(newSerialNumber);
			deviceRepository.save(device);

			DeviceSerialNumberChanged serialNumberChangedEvent = DeviceSerialNumberChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.serialNumber(newSerialNumber)
				.build();
			events.publishEvent(serialNumberChangedEvent);
		}
	}


	@Transactional
	public void updateHardwareConfigOfDevice(Integer deviceId, UpdateHardwareConfigDto updateHardwareConfigDto, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		Integer newModelAppleSiliconId = updateHardwareConfigDto.getModelAppleSiliconId();
		boolean updateModelAppleSilicon = !Objects.equals(newModelAppleSiliconId, device.getModelAppleSiliconId());
		if (updateModelAppleSilicon) {
			device.setModelAppleSiliconId(newModelAppleSiliconId);
		}

		Integer newModelAppleSiliconUnifiedMemoryId = updateHardwareConfigDto.getModelAppleSiliconUnifiedMemoryId();
		boolean updateModelAppleSiliconUnifiedMemory =
			!Objects.equals(newModelAppleSiliconUnifiedMemoryId, device.getModelAppleSiliconUnifiedMemoryId());
		if (updateModelAppleSiliconUnifiedMemory) {
			device.setModelAppleSiliconUnifiedMemoryId(newModelAppleSiliconUnifiedMemoryId);
		}

		Integer newModelStorageId = updateHardwareConfigDto.getModelStorageId();
		boolean updateModelStorage = !Objects.equals(newModelStorageId, device.getModelStorageId());
		if (updateModelStorage) {
			device.setModelStorageId(newModelStorageId);
		}

		Integer newModelColorId = updateHardwareConfigDto.getModelColorId();
		boolean updateModelColor = !Objects.equals(newModelColorId, device.getModelColorId());
		if (updateModelColor) {
			device.setModelColorId(newModelColorId);
		}

		if (updateModelAppleSilicon || updateModelAppleSiliconUnifiedMemory || updateModelStorage || updateModelColor) {
			deviceRepository.save(device);

			List<AbstractDeviceActivityEvent> deviceEvents = new ArrayList<>();
			if (updateModelAppleSilicon) {
				DeviceAppleSiliconChanged appleSiliconChanged = DeviceAppleSiliconChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.modelAppleSiliconId(newModelAppleSiliconId)
					.build();
				deviceEvents.add(appleSiliconChanged);
			}

			if (updateModelAppleSiliconUnifiedMemory) {
				DeviceUnifiedMemoryChanged unifiedMemoryChangedEvent = DeviceUnifiedMemoryChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.modelUnifiedMemoryId(newModelAppleSiliconUnifiedMemoryId)
					.build();
				deviceEvents.add(unifiedMemoryChangedEvent);
			}

			if (updateModelStorage) {
				DeviceStorageChanged storageChangedEvent = DeviceStorageChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.modelStorageId(newModelStorageId)
					.build();
				deviceEvents.add(storageChangedEvent);
			}

			if (updateModelColor) {
				DeviceColorChanged deviceColorChangedEvent = DeviceColorChanged.builder()
					.source(this)
					.actor(actor)
					.deviceId(deviceId)
					.modelColorId(newModelColorId)
					.build();
				deviceEvents.add(deviceColorChangedEvent);
			}

			deviceEvents.forEach(events::publishEvent);
		}
	}
}