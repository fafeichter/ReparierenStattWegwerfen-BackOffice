package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceSparePartRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.sparepart.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSparePartService {

	private final DeviceSparePartRepository deviceSparePartRepository;
	private final SparePartService sparePartService;

	public List<DeviceSparePartDto> load(Integer deviceId) {
		return deviceSparePartRepository.getSparePartsForDevice(deviceId)
			.stream()
			.map(deviceSparePart -> {
				NamedIdDto sparePart = sparePartService.getSparePartById(deviceSparePart.getSparePartId());
				return DeviceSparePartDto.builder()
					.deviceSparePartId(deviceSparePart.getId())
					.name(sparePart.getName())
					.priceNetto(deviceSparePart.getPriceNetto())
					.date(deviceSparePart.getDate())
					.build();
			}).toList();
	}
}