package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.controller.CreateDeviceSparePartDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceSparePartsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceSparePartAdded;
import at.reparierenstattwegwerfen.backoffice.device.internal.service.event.DeviceSparePartDeleted;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import at.reparierenstattwegwerfen.backoffice.sparepart.SparePartService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

	private final DeviceSparePartsRepository deviceSparePartsRepository;
	private final SparePartService sparePartService;
	private final DeviceRepository deviceRepository;
	private final ModelDetailsService modelDetailsService;
	private final ApplicationEventPublisher events;

	public List<DeviceSparePartDto> load(Integer deviceId) {
		return deviceSparePartsRepository.getSparePartsForDevice(deviceId)
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
		Set<Integer> alreadyAddedSparePartsIds = deviceSparePartsRepository
			.getSparePartsForDevice(deviceId).stream()
			.map(DeviceSpareParts::getSparePartId)
			.collect(toUnmodifiableSet());

		Integer modelId = deviceRepository.getReferenceById(deviceId).getModelId();
		Integer modelSeriesId = modelDetailsService.getModelSeries(modelId).getId();

		return sparePartService.getSparePartsForModelSeriesExcluding(
			modelSeriesId,
			alreadyAddedSparePartsIds);
	}

	@Transactional
	public void addDeviceSparePart(Integer deviceId, CreateDeviceSparePartDto newDeviceSparePartDto, UserDetails actor) {
		DeviceSpareParts deviceSpareParts = new DeviceSpareParts();
		deviceSpareParts.setDeviceId(deviceId);
		deviceSpareParts.setSparePartId(newDeviceSparePartDto.getSparePartId());
		deviceSpareParts.setPriceNetto(newDeviceSparePartDto.getPriceNetto());
		deviceSpareParts.setDate(LocalDateTime.now());

		Integer newDeviceSparePartsId = deviceSparePartsRepository.save(deviceSpareParts).getId();

		DeviceSparePartAdded deviceSparePartAddedEvent = DeviceSparePartAdded.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.deviceSparePartId(newDeviceSparePartsId)
			.build();
		events.publishEvent(deviceSparePartAddedEvent);
	}

	@Transactional
	public void deleteDeviceSparePart(Integer deviceId, Integer oldDeviceSparePartId, UserDetails actor) {
		DeviceSpareParts deviceSparePart = deviceSparePartsRepository.getReferenceById(oldDeviceSparePartId);
		String oldSparePartName = sparePartService.getSparePartById(deviceSparePart.getSparePartId()).getName();
		Double oldSparePartPriceNetto = deviceSparePart.getPriceNetto();

		DeviceSparePartDeleted deviceSparePartDeletedEvent = DeviceSparePartDeleted.builder()
			.source(this)
			.actor(actor)
			.deviceId(deviceId)
			.oldDeviceSparePartId(oldDeviceSparePartId)
			.oldDeviceSparePartName(oldSparePartName)
			.oldDeviceSparePartPriceNetto(oldSparePartPriceNetto)
			.build();
		events.publishEvent(deviceSparePartDeletedEvent);

		deviceSparePartsRepository.delete(deviceSparePart);
	}
}