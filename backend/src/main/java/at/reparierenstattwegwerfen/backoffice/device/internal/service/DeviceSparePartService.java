package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceSparePartRepository;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.sparepart.SparePartService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSparePartService {

	private final DeviceSparePartRepository deviceSparePartRepository;
	private final SparePartService sparePartService;
	private final DeviceRepository deviceRepository;
	private final ModelDetailsService modelDetailsService;

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

	public List<NamedIdDto> getAvailableSparePartsForDevice(@NonNull Integer deviceId) {
		Set<Integer> alreadyAddedSparePartsIds = deviceSparePartRepository
			.getSparePartsForDevice(deviceId).stream()
			.map(DeviceSpareParts::getSparePartId)
			.collect(toUnmodifiableSet());

		Integer modelId = deviceRepository.getReferenceById(deviceId).getModelId();
		Integer modelSeriesId = modelDetailsService.getModelSeries(modelId).getId();

		return sparePartService.getSparePartsForModelSeriesExcluding(
			modelSeriesId,
			alreadyAddedSparePartsIds);
	}
}