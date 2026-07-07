package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTags;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<NamedIdDto> getAllStatus() {
		return deviceStatusRepository.getAllStatus()
			.stream()
			.map(deviceStatus -> NamedIdDto.from(deviceStatus))
			.toList();
	}

	@Transactional
	public void updateStatusOfDevice(Integer deviceId, Integer newStatusId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceStatusChanged deviceStatusChanged = new DeviceStatusChanged(
			this, device.getId(), device.getStatus().getId(), newStatusId);
		device.setStatus(deviceStatusRepository.getReferenceById(newStatusId));

		deviceRepository.save(device);
		events.publishEvent(deviceStatusChanged);
	}

	@Transactional
	public void updateSerialNumber(Integer deviceId, String newSerialNumber) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setSerialNumber(newSerialNumber);

		deviceRepository.save(device);
	}

	@Transactional
	public void updateBattery(Integer deviceId, BatteryHealthDto newDeviceBaseBattery) {
		Device device = deviceRepository.getReferenceById(deviceId);
		device.setBatteryMaximumCapacity(newDeviceBaseBattery.getMaximumCapacity());
		device.setBatteryCycleCount(newDeviceBaseBattery.getCycleCount());

		if (newDeviceBaseBattery.determineStatusId() != null && device.getBatteryStatus() == null) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(newDeviceBaseBattery.determineStatusId()));

			BatteryStatusAutomaticallySet batteryStatusEvent = new BatteryStatusAutomaticallySet(
				this, deviceId, newDeviceBaseBattery.determineStatusId());
			events.publishEvent(batteryStatusEvent);
		}

		deviceRepository.save(device);
	}

	@Transactional
	public void updateBatteryStatus(Integer deviceId, Integer newBatteryStatusId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceBatteryStatusChanged batteryStatusChanged = new DeviceBatteryStatusChanged(this, deviceId,
			device.getBatteryStatus() != null ? device.getBatteryStatus().getId() : null, newBatteryStatusId);
		device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(newBatteryStatusId));

		deviceRepository.save(device);
		events.publishEvent(batteryStatusChanged);
	}

	@Transactional
	public void updateGrade(Integer deviceId, Integer newGradeId) {
		Device device = deviceRepository.getReferenceById(deviceId);
		DeviceGradeChanged deviceGradeChanged = new DeviceGradeChanged(this, deviceId,
			device.getGrade() != null ? device.getGrade().getId() : null, newGradeId);
		device.setGrade(deviceGradeRepository.getReferenceById(newGradeId));

		deviceRepository.save(device);
		events.publishEvent(deviceGradeChanged);
	}

	@Transactional
	public void addTag(Integer deviceId, Integer newTagId) {
		DeviceTags deviceTags = new DeviceTags();
		deviceTags.setDevice(deviceRepository.getReferenceById(deviceId));
		deviceTags.setDeviceTag(deviceTagRepository.getReferenceById(newTagId));
		DeviceTagAdded deviceTagAdded = new DeviceTagAdded(this, deviceId, newTagId);

		deviceTagsRepository.save(deviceTags);
		events.publishEvent(deviceTagAdded);
	}

	@Transactional
	public void deleteTag(Integer deviceId, Integer tagId) {
		deviceTagsRepository.deleteByDeviceAndDeviceTag(deviceRepository.getReferenceById(deviceId), deviceTagRepository.getReferenceById(tagId));
		DeviceTagRemoved deviceTagRemoved = new DeviceTagRemoved(this, deviceId, tagId);
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
}