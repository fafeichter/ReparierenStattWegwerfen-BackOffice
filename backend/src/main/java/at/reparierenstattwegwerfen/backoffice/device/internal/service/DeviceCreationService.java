package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceCreationService {

	private final DeviceRepository deviceRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final ApplicationEventPublisher events;

	@Transactional
	public Integer createDevice(CreateNewDeviceDto newDevice) {
		Device device = new Device();

		device.setBuyingDate(LocalDate.now());
		device.setUrl(newDevice.getUrl());
		device.setStatus(deviceStatusRepository.getReferenceById(1));

		device.setModelId(newDevice.getModelId());
		device.setModelColorId(newDevice.getModelColorId());
		device.setModelStorageId(newDevice.getModelStorageId());
		device.setModelAppleSiliconId(newDevice.getModelAppleSiliconId());
		device.setModelAppleSiliconUnifiedMemoryId(newDevice.getModelAppleSiliconUnifiedMemoryId());

		BatteryHealthDto batteryHealth = new BatteryHealthDto(
			newDevice.getBatteryMaximumCapacity(),
			newDevice.getBatteryCycleCount()
		);

		device.setBatteryMaximumCapacity(batteryHealth.getMaximumCapacity());
		device.setBatteryCycleCount(batteryHealth.getCycleCount());

		if (batteryHealth.determineStatusId() != null) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(batteryHealth.determineStatusId()));
		}

		device.setSerialNumber(newDevice.getSerialNumber());
		device.setPurchasePrice(newDevice.getPurchasePrice());
		device.setReportedDefect(newDevice.getDefect());
		device.setSellerBusinessPartnerId(newDevice.getSellerBusinessPartnerId());

		Integer newDeviceId = deviceRepository.save(device).getId();

		DeviceCreated deviceCreatedEvent = DeviceCreated.builder()
			.source(this)
			.deviceId(newDeviceId)
			.build();
		events.publishEvent(deviceCreatedEvent);

		if (batteryHealth.determineStatusId() != null) {
			BatteryStatusAutomaticallySet batteryStatusEvent = new BatteryStatusAutomaticallySet(
				this, newDeviceId, batteryHealth.determineStatusId());
			events.publishEvent(batteryStatusEvent);
		}

		return newDeviceId;
	}
}
