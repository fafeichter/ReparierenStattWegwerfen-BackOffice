package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceTagsService {

	private final DeviceTagRepository deviceTagRepository;

	public List<DeviceTagDto> getTagsForDevice(Integer deviceId) {
		return deviceTagRepository.getTagsForDevice(deviceId).stream().map(tag ->
				DeviceTagDto.builder()
					.id(tag.getId())
					.name(tag.getName())
					.build())
			.toList();
	}
}
