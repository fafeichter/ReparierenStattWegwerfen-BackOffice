package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGrade;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGradeChanged;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGradeRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.hardware.*;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOfferedForSaleDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceToFinishDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.UpdateHardwareConfigDto;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 06.07.2026
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
	private final ModelDetailsService modelDetailsService;

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public List<DeviceToFinishDto> getDevicesToFinish() {
		return deviceRepository.findAllByStatusIdIn(List.of(2, 3, 4))
			.stream()
			.map(device ->
				DeviceToFinishDto.builder()
					.deviceId(device.getId())
					.status(NamedIdDto.from(device.getStatus()))
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DeviceOfferedForSaleDto> getDevicesOfferedForSale() {
		return deviceRepository.findAllByStatusIdIn(List.of(5))
			.stream()
			.map(device ->
				DeviceOfferedForSaleDto.builder()
					.deviceId(device.getId())
					.statusDate(device.getStatusDate().toLocalDate())
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}


	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void setDeviceStatusDate(DeviceStatusChanged event) {
		Device device = deviceRepository.getReferenceById(event.getDeviceId());
		device.setStatusDate(event.getExactTimestamp());
		deviceRepository.save(device);
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