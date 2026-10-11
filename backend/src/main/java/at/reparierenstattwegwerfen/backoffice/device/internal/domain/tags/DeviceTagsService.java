package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 11.10.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceTagsService {

	private final DeviceRepository deviceRepository;
	private final DeviceTagRepository deviceTagRepository;
	private final DeviceTagsRepository deviceTagsRepository;
	private final ApplicationEventPublisher events;
	private final ModelDetailsService modelDetailsService;

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
		deviceTagsRepository.deleteByDeviceTagId(oldDeviceTagId);

		DeviceTagRemoved deviceTagRemovedEvent = DeviceTagRemoved.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.oldDeviceTagId(oldDeviceTagId)
			.build();
		events.publishEvent(deviceTagRemovedEvent);
	}

	@Transactional(readOnly = true)
	public List<NamedIdDto> getAvailableTagsForDevice(Integer deviceId) {
		Integer deviceModelId = deviceRepository.getReferenceById(deviceId).getModelId();
		Integer deviceModelSeriesId = modelDetailsService.getModelSeries(deviceModelId).getId();

		return deviceTagRepository.getAvailableTagsForDevice(deviceId, deviceModelSeriesId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}
}