package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTags;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceStatusService {

	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final DeviceGradeRepository deviceGradeRepository;
	private final DeviceTagRepository deviceTagRepository;
	private final DeviceTagsRepository deviceTagsRepository;
	private final ApplicationEventPublisher events;

	public List<NamedIdDto> getAllNonSystemStatus() {
		return deviceStatusRepository.getAllNonSystemStatus()
			.stream()
			.map(deviceStatus -> NamedIdDto.from(deviceStatus))
			.toList();
	}

	@Transactional
	public void updateStatusOfDevice(Integer deviceId, Integer newStatusId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceStatusChanged deviceStatusChanged = new DeviceStatusChanged(
			this, actor, device.getId(), newStatusId);
		device.setStatus(deviceStatusRepository.getReferenceById(newStatusId));

		deviceRepository.save(device);
		events.publishEvent(deviceStatusChanged);
	}

	@ApplicationModuleListener
	public void on(DeviceStatusChanged event) {
		Device device = deviceRepository.getReferenceById(event.getDeviceId());
		if (event.getNewStatusId() == 6) {
			device.setSellingDate(LocalDate.now());
			deviceRepository.save(device);
		}
	}

	@Transactional
	public void updateSerialNumber(Integer deviceId, String newSerialNumber, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setSerialNumber(newSerialNumber);

		deviceRepository.save(device);
	}

	@Transactional
	public void updateBattery(Integer deviceId, BatteryHealthDto newDeviceBaseBattery, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setBatteryMaximumCapacity(newDeviceBaseBattery.getMaximumCapacity());
		device.setBatteryCycleCount(newDeviceBaseBattery.getCycleCount());

		if (newDeviceBaseBattery.determineStatusId() != null && device.getBatteryStatus() == null) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(newDeviceBaseBattery.determineStatusId()));

			DeviceBatteryStatusChanged batteryStatusEvent = new DeviceBatteryStatusChanged(
				this, SystemUser.get(), deviceId, newDeviceBaseBattery.determineStatusId());
			events.publishEvent(batteryStatusEvent);
		}

		deviceRepository.save(device);
	}

	@Transactional
	public void updateBatteryStatus(Integer deviceId, Integer newBatteryStatusId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceBatteryStatusChanged batteryStatusChanged = new DeviceBatteryStatusChanged(this, actor, deviceId, newBatteryStatusId);
		device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(newBatteryStatusId));

		deviceRepository.save(device);
		events.publishEvent(batteryStatusChanged);
	}

	@Transactional
	public void updateGrade(Integer deviceId, Integer newGradeId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceGradeChanged deviceGradeChanged = new DeviceGradeChanged(this, actor, deviceId, newGradeId);
		device.setGrade(deviceGradeRepository.getReferenceById(newGradeId));

		deviceRepository.save(device);
		events.publishEvent(deviceGradeChanged);
	}

	@Transactional
	public void addTag(Integer deviceId, Integer newTagId, UserDetails actor) {
		DeviceTags deviceTags = new DeviceTags();
		deviceTags.setDevice(deviceRepository.getReferenceById(deviceId));
		deviceTags.setDeviceTag(deviceTagRepository.getReferenceById(newTagId));
		DeviceTagAdded deviceTagAdded = new DeviceTagAdded(this, actor, deviceId, newTagId);

		deviceTagsRepository.save(deviceTags);
		events.publishEvent(deviceTagAdded);
	}

	@Transactional
	public void deleteTag(Integer deviceId, Integer tagId, UserDetails actor) {
		deviceTagsRepository.deleteByDeviceAndDeviceTag(deviceRepository.getReferenceById(deviceId), deviceTagRepository.getReferenceById(tagId));
		DeviceTagRemoved deviceTagRemoved = new DeviceTagRemoved(this, actor, deviceId, tagId);
		events.publishEvent(deviceTagRemoved);
	}

	public List<NamedIdDto> getAvailableTags(Integer deviceId) {
		List<DeviceTag> allTags = deviceTagRepository.findAll();
		List<Integer> alreadyUsedTagIds = deviceTagRepository.getTagsForDevice(deviceId)
			.stream()
			.map(DeviceTag::getId)
			.toList();

		return allTags.stream()
			.filter(tag -> !alreadyUsedTagIds.contains(tag.getId()))
			.map(a -> NamedIdDto.from(a))
			.toList();
	}

	@Transactional
	public void updateHardwareConfigOfDevice(Integer deviceId, UpdateHardwareConfigDto updateHardwareConfigDto) {
		Device device = deviceRepository.getReferenceById(deviceId);

		device.setModelAppleSiliconId(updateHardwareConfigDto.getModelAppleSiliconId());
		device.setModelAppleSiliconUnifiedMemoryId(updateHardwareConfigDto.getModelAppleSiliconUnifiedMemoryId());
		device.setModelStorageId(updateHardwareConfigDto.getModelStorageId());
		device.setModelColorId(updateHardwareConfigDto.getModelColorId());

		deviceRepository.save(device);
	}
}