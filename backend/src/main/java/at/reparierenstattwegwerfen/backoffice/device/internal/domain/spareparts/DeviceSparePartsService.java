package at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.CreateDeviceSparePartDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSparePartDto;
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
public class DeviceSparePartsService {

	private final DeviceInstalledSparePartRepository deviceInstalledSparePartRepository;
	private final SparePartService sparePartService;
	private final DeviceRepository deviceRepository;
	private final ModelDetailsService modelDetailsService;
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public List<DeviceSparePartDto> load(Integer deviceId) {
		return deviceInstalledSparePartRepository.getSparePartsForDevice(deviceId)
			.stream()
			.map(deviceSparePart -> {
				NamedIdDto sparePart = sparePartService.getSparePartById(deviceSparePart.getSparePartId());
				return DeviceSparePartDto.builder()
					.deviceSparePartId(deviceSparePart.getId())
					.name(sparePart.name())
					.priceNetto(deviceSparePart.getPriceNetto())
					.date(deviceSparePart.getDate())
					.build();
			}).toList();
	}

	@Transactional(readOnly = true)
	public List<NamedIdDto> getAvailableSparePartsForDevice(@NonNull Integer deviceId) {
		Set<Integer> alreadyAddedSparePartsIds = deviceInstalledSparePartRepository
			.getSparePartsForDevice(deviceId)
			.stream()
			.map(DeviceInstalledSparePart::getSparePartId)
			.collect(toUnmodifiableSet());

		Integer modelId = deviceRepository.getById(deviceId).getModelId();
		Integer modelSeriesId = modelDetailsService.getModelSeries(modelId).id();

		return sparePartService.getSparePartsForModelSeriesExcluding(
			modelSeriesId,
			alreadyAddedSparePartsIds);
	}

	@Transactional
	public void addDeviceSparePart(Integer deviceId, CreateDeviceSparePartDto newDeviceSparePartDto, UserDetails actor) {
		DeviceInstalledSparePart deviceInstalledSparePart = new DeviceInstalledSparePart();
		deviceInstalledSparePart.setDeviceId(deviceId);
		deviceInstalledSparePart.setSparePartId(newDeviceSparePartDto.getSparePartId());
		deviceInstalledSparePart.setPriceNetto(newDeviceSparePartDto.getPriceNetto());
		deviceInstalledSparePart.setDate(LocalDateTime.now());

		Integer newDeviceSparePartsId = deviceInstalledSparePartRepository.save(deviceInstalledSparePart).getId();

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
		DeviceInstalledSparePart deviceSparePart = deviceInstalledSparePartRepository.getById(oldDeviceSparePartId);
		String oldSparePartName = sparePartService.getSparePartById(deviceSparePart.getSparePartId()).name();
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

		deviceInstalledSparePartRepository.deleteById(oldDeviceSparePartId);
	}
}