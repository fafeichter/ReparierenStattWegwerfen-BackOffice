package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceSparePartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceSparePartService {

	private final DeviceSparePartRepository deviceSparePartRepository;

	public List<DeviceSparePartDto> load(Integer deviceId) {
		return deviceSparePartRepository.getSparePartsForDevice(deviceId).stream().map(sparePart ->
				DeviceSparePartDto.builder()
					.sparePartId(sparePart.getId())
					.name(sparePart.getName())
					.timestamp(sparePart.getTimestamp())
					.build())
			.toList();
	}
}