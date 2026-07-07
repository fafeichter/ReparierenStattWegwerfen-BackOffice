package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.controller.DeviceActivityDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivityType;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.*;
import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final Mustache.Compiler mustacheCompiler;

	@ApplicationModuleListener
	public void on(DeviceCreated event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(1);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, Integer> context = Map.of("deviceId", event.getDeviceId());

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(DeviceStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(2);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("oldStatus", deviceStatusRepository.getReferenceById(event.getOldStatusId()).getName());
			put("newStatus", deviceStatusRepository.getReferenceById(event.getNewStatusId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(BatteryStatusAutomaticallySet event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(3);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("newBatteryStatus", deviceBatteryStatusRepository.getReferenceById(event.getBatteryStatusId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(DeviceBatteryStatusChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(4);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("oldBatteryStatus", event.getOldBatteryStatusId() != null ? deviceBatteryStatusRepository.getReferenceById(event.getOldBatteryStatusId()).getName() : "-");
			put("newBatteryStatus", deviceBatteryStatusRepository.getReferenceById(event.getNewBatteryStatusId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(DeviceGradeChanged event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(5);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("oldGrade", event.getOldGradeId() != null ? deviceGradeRepository.getReferenceById(event.getOldGradeId()).getName() : "-");
			put("newGrade", deviceGradeRepository.getReferenceById(event.getNewGradeId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(DeviceTagAdded event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(6);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("newTag", deviceTagRepository.getReferenceById(event.getNewTagId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	@EventListener
	public void on(DeviceTagRemoved event) {
		DeviceActivity deviceActivity = new DeviceActivity(event.getTimestamp());
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));

		DeviceActivityType activityType = deviceActivityTypeRepository.getReferenceById(7);
		String templateString = activityType.getDescriptionTemplate();
		Map<String, String> context = new HashMap<>() {{
			put("tag", deviceTagRepository.getReferenceById(event.getTagId()).getName());
		}};

		deviceActivity.setName(mustacheCompiler.compile(templateString).execute(context));
		deviceActivity.setActivityType(activityType);

		deviceActivityRepository.save(deviceActivity);
	}

	public List<DeviceActivityDto> getForDevice(Integer deviceId) {
		return deviceActivityRepository.getByIdWithRelations(deviceId).stream().map(activity ->
				DeviceActivityDto.builder()
					.id(activity.getId())
					.name(activity.getActivityType().getName())
					.description(activity.getName())
					.date(activity.getDate())
					.build())
			.toList();
	}
}
