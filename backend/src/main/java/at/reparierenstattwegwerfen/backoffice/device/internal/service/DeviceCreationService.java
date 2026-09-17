package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.CreateBusinessPartnerPlaceholderDto;
import at.reparierenstattwegwerfen.backoffice.device.DeviceBuyingService;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceStatusRepository;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
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

		device.setBuyingDate(newDevice.getBuyingDate());
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

		boolean batteryStatusCanAutomaticallyBeSet = batteryHealth.determineStatusId() != null;
		if (batteryStatusCanAutomaticallyBeSet) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(batteryHealth.determineStatusId()));
		}

		device.setSerialNumber(newDevice.getSerialNumber());
		device.setPurchasePrice(newDevice.getPurchasePrice());
		device.setReportedDefect(newDevice.getDefect());

		CreateBusinessPartnerPlaceholderDto businessPartnerPlaceholder = new CreateBusinessPartnerPlaceholderDto(
			newDevice.getBusinessPartnerPlaceholder().getFirstName(),
			newDevice.getBusinessPartnerPlaceholder().getLastName()

		);
		Integer sellerBusinessPartnerId = businessPartnerService.createBusinessPartnerPlaceholder(businessPartnerPlaceholder);
		device.setSellerBusinessPartnerId(sellerBusinessPartnerId);

		Integer newDeviceId = deviceRepository.save(device).getId();

		DeviceCreated deviceCreatedEvent = DeviceCreated.builder()
			.source(this)
			.actor(actor)
			.deviceId(newDeviceId)
			.build();
		events.publishEvent(deviceCreatedEvent);

		if (batteryStatusCanAutomaticallyBeSet) {
			DeviceBatteryStatusChanged batteryStatusEvent = new DeviceBatteryStatusChanged(
				this, SystemUser.get(), newDeviceId, batteryHealth.determineStatusId());
			events.publishEvent(batteryStatusEvent);
		}

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
