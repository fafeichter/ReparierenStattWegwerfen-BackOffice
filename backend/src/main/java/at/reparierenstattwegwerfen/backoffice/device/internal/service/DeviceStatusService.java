package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.AbstractDeviceActivityEvent;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.*;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional
	public void updateStatusOfDevice(Integer deviceId, Integer newStatusId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		if (!newStatusId.equals(device.getStatus().getId())) {
			device.setStatus(deviceStatusRepository.getReferenceById(newStatusId));
			deviceRepository.save(device);

			DeviceStatusChanged deviceStatusChangedEvent = DeviceStatusChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.newStatusId(newStatusId)
				.build();
			events.publishEvent(deviceStatusChangedEvent);
		}
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void on(DeviceStatusChanged event) {
		Device device = deviceRepository.getReferenceById(event.getDeviceId());

		Integer newStatusId = event.getNewStatusId();
		if (!Objects.equals(newStatusId, device.getStatus().getId()) && newStatusId == 6) {
			LocalDate sellingDate = LocalDate.now();
			device.setSellingDate(sellingDate);
			deviceRepository.save(device);

			DeviceSellingDateChanged sellingDateChangedEvent = DeviceSellingDateChanged.builder()
				.source(this)
				.actor(SystemUser.get())
				.deviceId(event.getDeviceId())
				.sellingDate(sellingDate)
				.build();
			events.publishEvent(sellingDateChangedEvent);
		}
	}

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
	public void updateBattery(Integer deviceId, BatteryHealthDto newDeviceBaseBattery, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		if (!Objects.equals(newDeviceBaseBattery.getCycleCount(), device.getBatteryCycleCount()) ||
			!Objects.equals(newDeviceBaseBattery.getMaximumCapacity(), device.getBatteryMaximumCapacity())) {

			device.setBatteryMaximumCapacity(newDeviceBaseBattery.getMaximumCapacity());
			device.setBatteryCycleCount(newDeviceBaseBattery.getCycleCount());

			boolean batteryStatusAutomaticallySetOrUpdated =
				(newDeviceBaseBattery.determineStatusId() != null && device.getBatteryStatus() == null) ||
					(newDeviceBaseBattery.determineStatusId() == null && device.getBatteryStatus() != null) ||
					(!Objects.equals(newDeviceBaseBattery.determineStatusId(), device.getBatteryStatus().getId()));

			if (batteryStatusAutomaticallySetOrUpdated) {
				device.setBatteryStatus(newDeviceBaseBattery.determineStatusId() != null ?
					deviceBatteryStatusRepository.getReferenceById(newDeviceBaseBattery.determineStatusId()) : null);
			}

			deviceRepository.save(device);

			DeviceBatteryHealthChanged batteryHealthChangedEvent = DeviceBatteryHealthChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.maximumCapacity(newDeviceBaseBattery.getMaximumCapacity())
				.cycleCount(newDeviceBaseBattery.getCycleCount())
				.build();
			events.publishEvent(batteryHealthChangedEvent);

			if (batteryStatusAutomaticallySetOrUpdated) {
				DeviceBatteryStatusChanged deviceBatteryStatusChangedEvent = DeviceBatteryStatusChanged.builder()
					.source(this)
					.actor(SystemUser.get())
					.deviceId(deviceId)
					.newBatteryStatusId(newDeviceBaseBattery.determineStatusId())
					.build();
				events.publishEvent(deviceBatteryStatusChangedEvent);
			}
		}
	}

	@Transactional
	public void updateBatteryStatus(Integer deviceId, Integer newBatteryStatusId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		Integer currentBatteryStatusId = Optional.ofNullable(device.getBatteryStatus())
			.map(DeviceBatteryStatus::getId)
			.orElse(null);

		if (!Objects.equals(currentBatteryStatusId, newBatteryStatusId)) {
			device.setBatteryStatus(deviceBatteryStatusRepository.getReferenceById(newBatteryStatusId));
			deviceRepository.save(device);

			DeviceBatteryStatusChanged deviceBatteryStatusChangedEvent = DeviceBatteryStatusChanged.builder()
				.source(this).actor(actor)
				.deviceId(deviceId)
				.newBatteryStatusId(newBatteryStatusId)
				.build();
			events.publishEvent(deviceBatteryStatusChangedEvent);
		}
	}

	@Transactional
	public void updateGrade(Integer deviceId, Integer newGradeId, UserDetails actor) {
		Device device = deviceRepository.getReferenceById(deviceId);

		Integer currentGradeId = Optional.ofNullable(device.getGrade())
			.map(DeviceGrade::getId)
			.orElse(null);

		if (!Objects.equals(currentGradeId, newGradeId)) {
			device.setGrade(deviceGradeRepository.getReferenceById(newGradeId));
			deviceRepository.save(device);

			DeviceGradeChanged deviceGradeChangedEvent = DeviceGradeChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.newGradeId(newGradeId)
				.build();
			events.publishEvent(deviceGradeChangedEvent);
		}
	}

	@Transactional
	public void addTag(Integer deviceId, Integer newDeviceTagId, UserDetails actor) {
		DeviceTags deviceTags = new DeviceTags();
		deviceTags.setDevice(deviceRepository.getReferenceById(deviceId));
		deviceTags.setDeviceTag(deviceTagRepository.getReferenceById(newDeviceTagId));
		deviceTagsRepository.save(deviceTags);

		DeviceTagAdded deviceTagAddedEvent = DeviceTagAdded.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.newDeviceTagId(newDeviceTagId)
			.build();
		events.publishEvent(deviceTagAddedEvent);
	}

	@Transactional
	public void removeTag(Integer deviceId, Integer oldDeviceTagId, UserDetails actor) {
		deviceTagsRepository.deleteByDeviceAndDeviceTag(deviceRepository.getReferenceById(deviceId),
			deviceTagRepository.getReferenceById(oldDeviceTagId));

		DeviceTagRemoved deviceTagRemovedEvent = DeviceTagRemoved.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.oldDeviceTagId(oldDeviceTagId)
			.build();
		events.publishEvent(deviceTagRemovedEvent);
	}

	public List<NamedIdDto> getAvailableTags(Integer deviceId) {
		List<DeviceTag> allTags = deviceTagRepository.findAll();
		List<Integer> alreadyUsedTagIds = deviceTagRepository.getTagsForDevice(deviceId)
			.stream()
			.map(DeviceTag::getId)
			.toList();

		return allTags.stream()
			.filter(tag -> !alreadyUsedTagIds.contains(tag.getId()))
			.map(NamedIdDto::from)
			.toList();
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
					.modelColorId(newModelStorageId)
					.build();
				deviceEvents.add(deviceColorChangedEvent);
			}

			deviceEvents.forEach(events::publishEvent);
		}
	}
}