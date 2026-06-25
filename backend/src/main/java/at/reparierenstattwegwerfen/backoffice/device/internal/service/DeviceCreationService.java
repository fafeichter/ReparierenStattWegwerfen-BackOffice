package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.DeviceStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.controller.CreateNewDevice;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceCreationService {

	private final DeviceRepository deviceRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;

	@Transactional
	public Integer createDevice(CreateNewDevice newDevice) {
		Device device = new Device();

		device.setUrl(newDevice.getUrl());
		device.setStatus(DeviceStatus.ORDERED);

		device.setModelId(newDevice.getModelId());
		device.setModelColorId(newDevice.getModelColorId());
		device.setModelStorageId(newDevice.getModelStorageId());
		device.setModelAppleSiliconId(newDevice.getModelAppleSiliconId());
		device.setModelAppleSiliconUnifiedMemoryId(newDevice.getModelAppleSiliconUnifiedMemoryId());
		if (newDevice.getBatteryMaximumCapacity() != null)
			if (newDevice.getBatteryMaximumCapacity() >= 90) {
				device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(1));
			} else {
				if (newDevice.getBatteryMaximumCapacity() <= 80 ||
					(newDevice.getBatteryCycleCount() != null && newDevice.getBatteryCycleCount() >= 500)) {
					device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(2));
				}
			}
		device.setBatteryMaximumCapacity(newDevice.getBatteryMaximumCapacity());
		device.setBatteryCycleCount(newDevice.getBatteryCycleCount());
		device.setSerialNumber(newDevice.getSerialNumber());
		device.setPurchasePrice(newDevice.getPurchasePrice());
		device.setReportedDefect(newDevice.getDefect());
		device.setSellerBusinessPartnerId(newDevice.getSellerBusinessPartnerId());

		return deviceRepository.save(device).getId();
	}
}
