package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.controller.DeviceActivityDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceActivityRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceActivityTypeRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceActivityService {

	private final DeviceActivityRepository deviceActivityRepository;
	private final DeviceActivityTypeRepository deviceActivityTypeRepository;
	private final DeviceRepository deviceRepository;
	private final Mustache.Compiler mustacheCompiler;

	@ApplicationModuleListener
	public void on(DeviceCreated event) {
		DeviceActivity deviceActivity = new DeviceActivity();
		deviceActivity.setDevice(deviceRepository.getReferenceById(event.getDeviceId()));
		deviceActivity.setName(renderDynamicTemplate(event.getDeviceId()));
		deviceActivity.setActivityType(deviceActivityTypeRepository.getReferenceById(1));
		deviceActivity.setDate(Instant.ofEpochMilli(event.getTimestamp())
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime());

		deviceActivityRepository.save(deviceActivity);
	}

	public String renderDynamicTemplate(Integer deviceId) {
		String templateString = deviceActivityTypeRepository.getReferenceById(1).getDescriptionTemplate();
		Map<String, Integer> context = Map.of("deviceId", deviceId);
		return mustacheCompiler.compile(templateString).execute(context);
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
