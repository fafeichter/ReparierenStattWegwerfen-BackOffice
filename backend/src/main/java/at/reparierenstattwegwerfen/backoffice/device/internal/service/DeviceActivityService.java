package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
@RegisterReflectionForBinding(DeviceCreated.class)
public class DeviceActivityService {

	private final DeviceActivityRepository deviceActivityRepository;
	private final DeviceActivityTypeRepository deviceActivityTypeRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceStatusRepository deviceStatusRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final DeviceGradeRepository deviceGradeRepository;
	private final DeviceTagRepository deviceTagRepository;

	@ApplicationModuleListener
	public void on(DeviceCreated event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName("#" + event.getDeviceId());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(1));

		deviceActivityRepository.save(deviceActivity);
	}

	@ApplicationModuleListener
	public void on(DeviceStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName(deviceStatusRepository.getReferenceById(event.getNewStatusId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(2));

		deviceActivityRepository.save(deviceActivity);
	}

	@ApplicationModuleListener
	public void on(DeviceBatteryStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName(deviceBatteryStatusRepository.getReferenceById(event.getNewBatteryStatusId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(3));

		deviceActivityRepository.save(deviceActivity);
	}

	@ApplicationModuleListener
	public void on(DeviceGradeChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName(deviceGradeRepository.getReferenceById(event.getNewGradeId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(4));

		deviceActivityRepository.save(deviceActivity);
	}

	@ApplicationModuleListener
	public void on(DeviceTagAdded event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName(deviceTagRepository.getReferenceById(event.getNewTagId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(5));

		deviceActivityRepository.save(deviceActivity);
	}

	@ApplicationModuleListener
	public void on(DeviceTagRemoved event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp(), event.getActor());
		deviceActivity.setName(deviceTagRepository.getReferenceById(event.getTagId()).getName());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(6));

		deviceActivityRepository.save(deviceActivity);
	}

	public List<DeviceActivityDto> getForDevice(Integer deviceId) {
		return deviceActivityRepository.getByIdWithRelations(deviceId).stream().map(activity ->
				DeviceActivityDto.builder()
					.id(activity.getId())
					.action(activity.getActivityType().getName())
					.value(activity.getName())
					.actor(activity.getActor())
					.date(activity.getDate())
					.build())
			.toList();
	}
}