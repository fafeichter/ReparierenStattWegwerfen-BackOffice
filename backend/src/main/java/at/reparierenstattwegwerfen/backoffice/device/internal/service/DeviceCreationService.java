package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerPlaceholderDto;
import at.reparierenstattwegwerfen.backoffice.device.DeviceBuyingService;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.*;
import at.reparierenstattwegwerfen.backoffice.event.DeviceBusinessPartnerSetAsSeller;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceCreationService implements DeviceBuyingService {

	private final DeviceRepository deviceRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final BusinessPartnerService businessPartnerService;
	private final ApplicationEventPublisher events;

	@Transactional
	public Integer createDevice(CreateNewDeviceDto newDevice, UserDetails actor) {
		Device device = new Device();
		List<AbstractDeviceActivityEvent> deviceEvents = new ArrayList<>();
		List<ApplicationEvent> businessPartnerEvents = new ArrayList<>();
		UserDetails system = SystemUser.get();

		device.setBuyingDate(newDevice.getBuyingDate());
		device.setUrl(newDevice.getUrl());
		device.setStatus(deviceStatusRepository.getReferenceById(1));
		device.setStatusDate(LocalDateTime.now());

		device.setModelId(newDevice.getModelId());

		boolean setAppleSilicon = newDevice.getModelAppleSiliconId() != null;
		boolean setUnifiedMemory = newDevice.getModelAppleSiliconUnifiedMemoryId() != null;
		boolean setModelStorage = newDevice.getModelStorageId() != null;
		boolean setModelColor = newDevice.getModelColorId() != null;
		BatteryHealthDto batteryHealth = new BatteryHealthDto(
			newDevice.getBatteryMaximumCapacity(),
			newDevice.getBatteryCycleCount()
		);
		boolean setBatteryMaximumCapacity = batteryHealth.getMaximumCapacity() != null;
		boolean setBatteryCycles = batteryHealth.getCycleCount() != null;
		boolean setBatteryStatus = batteryHealth.determineStatusId() != null;

		if (setAppleSilicon) {
			device.setModelAppleSiliconId(newDevice.getModelAppleSiliconId());
		}

		if (setUnifiedMemory) {
			device.setModelAppleSiliconUnifiedMemoryId(newDevice.getModelAppleSiliconUnifiedMemoryId());
		}

		if (setModelStorage) {
			device.setModelStorageId(newDevice.getModelStorageId());
		}

		if (setModelColor) {
			device.setModelColorId(newDevice.getModelColorId());
		}

		if (setBatteryMaximumCapacity) {
			device.setBatteryMaximumCapacity(batteryHealth.getMaximumCapacity());
		}

		if (setBatteryCycles) {
			device.setBatteryCycleCount(batteryHealth.getCycleCount());
		}

		if (setBatteryStatus) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(batteryHealth.determineStatusId()));
		}

		boolean setSerialNumber = newDevice.getSerialNumber() != null;
		if (setSerialNumber) {
			device.setSerialNumber(newDevice.getSerialNumber());
		}
		device.setPurchasePrice(newDevice.getPurchasePrice());
		device.setReportedDefect(newDevice.getDefect());

		CreateBusinessPartnerPlaceholderDto businessPartnerPlaceholder = new CreateBusinessPartnerPlaceholderDto(
			newDevice.getBusinessPartnerPlaceholder().getFirstName(),
			newDevice.getBusinessPartnerPlaceholder().getLastName()

		);
		Integer sellerBusinessPartnerId = businessPartnerService.createBusinessPartnerPlaceholder(
			businessPartnerPlaceholder, system);
		device.setSellerBusinessPartnerId(sellerBusinessPartnerId);

		Integer newDeviceId = deviceRepository.save(device).getId();

		DeviceCreated deviceCreatedEvent = DeviceCreated.builder()
			.source(this)
			.actor(actor)
			.deviceId(newDeviceId)
			.build();
		deviceEvents.add(deviceCreatedEvent);

		DeviceBuyingDateChanged deviceBuyingDateChangedEvent = DeviceBuyingDateChanged.builder()
			.source(this)
			.actor(actor)
			.deviceId(newDeviceId)
			.buyingDate(newDevice.getBuyingDate())
			.build();
		deviceEvents.add(deviceBuyingDateChangedEvent);

		if (setAppleSilicon) {
			DeviceAppleSiliconChanged appleSiliconChanged = DeviceAppleSiliconChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.modelAppleSiliconId(newDevice.getModelAppleSiliconId())
				.build();
			deviceEvents.add(appleSiliconChanged);
		}

		if (setUnifiedMemory) {
			DeviceUnifiedMemoryChanged unifiedMemoryChangedEvent = DeviceUnifiedMemoryChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.modelUnifiedMemoryId(newDevice.getModelAppleSiliconUnifiedMemoryId())
				.build();
			deviceEvents.add(unifiedMemoryChangedEvent);
		}

		if (setModelStorage) {
			DeviceStorageChanged storageChangedEvent = DeviceStorageChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.modelStorageId(newDevice.getModelStorageId())
				.build();
			deviceEvents.add(storageChangedEvent);
		}

		if (setModelColor) {
			DeviceColorChanged deviceColorChangedEvent = DeviceColorChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.modelColorId(newDevice.getModelColorId())
				.build();
			deviceEvents.add(deviceColorChangedEvent);
		}

		if (setBatteryMaximumCapacity || setBatteryCycles) {
			DeviceBatteryHealthChanged batteryHealthChangedEvent = DeviceBatteryHealthChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.maximumCapacity(batteryHealth.getMaximumCapacity())
				.cycleCount(batteryHealth.getCycleCount())
				.build();
			deviceEvents.add(batteryHealthChangedEvent);
		}

		if (setBatteryStatus) {
			DeviceBatteryStatusChanged deviceBatteryStatusChangedEvent = DeviceBatteryStatusChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.newBatteryStatusId(batteryHealth.determineStatusId())
				.build();
			deviceEvents.add(deviceBatteryStatusChangedEvent);
		}

		if (setSerialNumber) {
			DeviceSerialNumberChanged serialNumberChangedEvent = DeviceSerialNumberChanged.builder()
				.source(this)
				.actor(system)
				.deviceId(newDeviceId)
				.serialNumber(newDevice.getSerialNumber())
				.build();
			deviceEvents.add(serialNumberChangedEvent);
		}

		DeviceReportedDefectChanged reportedDefectChangedEvent = DeviceReportedDefectChanged.builder()
			.source(this)
			.actor(system)
			.deviceId(newDeviceId)
			.reportedDefect(newDevice.getDefect())
			.build();
		deviceEvents.add(reportedDefectChangedEvent);

		DeviceBusinessPartnerSetAsSeller businessPartnerSetAsSellerEvent = DeviceBusinessPartnerSetAsSeller.builder()
			.source(this)
			.actor(system)
			.sellerBusinessPartnerId(sellerBusinessPartnerId)
			.deviceId(newDeviceId)
			.build();
		businessPartnerEvents.add(businessPartnerSetAsSellerEvent);

		deviceEvents.forEach(events::publishEvent);
		businessPartnerEvents.forEach(events::publishEvent);

		return newDeviceId;
	}

	@Transactional
	@Override
	public void setBuyerAddressForDevice(Integer deviceId, Integer buyerBusinessPartnerId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setBuyerBusinessPartnerId(buyerBusinessPartnerId);
		deviceRepository.save(device);
	}
}
