package at.reparierenstattwegwerfen.backoffice.device.internal.domain.search;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityService;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.earnings.DeviceEarning;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceInstalledSparePart;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceInstalledSparePartRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOfferedForSaleDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOrderedDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceToFinishDto;
import at.reparierenstattwegwerfen.backoffice.model.ModelAppleSiliconDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.ModelStorageDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.ModelUnifiedMemoryDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 22.09.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSearchService {

	private final DeviceRepository deviceRepository;
	private final ModelDetailsService modelDetailsService;
	private final ModelAppleSiliconDetailsService modelAppleSiliconDetailsService;
	private final ModelUnifiedMemoryDetailsService modelUnifiedMemoryDetailsService;
	private final ModelStorageDetailsService modelStorageDetailsService;
	private final DeviceActivityService deviceActivityService;
	private final DeviceInstalledSparePartRepository deviceInstalledSparePartRepository;

	@Transactional(readOnly = true)
	public Page<DeviceDto> search(Pageable page, Boolean includeInactiveDevices) {
		return deviceRepository.search(page, includeInactiveDevices)
			.map(device -> {
				List<DeviceInstalledSparePart> spareParts = deviceInstalledSparePartRepository
					.getSparePartsForDevice(device.getId());
				DeviceEarning deviceEarning = new DeviceEarning(device, spareParts);

				return DeviceDto.builder()
					.deviceId(device.getId())
					.status(NamedIdDto.from(device.getStatus()))
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device
						.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
					.earning(deviceEarning.getEarning())
					.lastActivity(deviceActivityService.getLastActivityDateForDevice(device.getId()))
					.build();
			});
	}

	@Transactional(readOnly = true)
	public List<DeviceToFinishDto> getDevicesToFinish() {
		return deviceRepository.getDevicesToFinish()
			.stream()
			.map(device ->
				DeviceToFinishDto.builder()
					.deviceId(device.getId())
					.status(NamedIdDto.from(device.getStatus()))
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device
						.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DeviceOfferedForSaleDto> getDevicesOfferedForSale() {
		return deviceRepository.getDevicesOfferedForSale()
			.stream()
			.map(device ->
				DeviceOfferedForSaleDto.builder()
					.deviceId(device.getId())
					.statusDate(device.getStatusDate().toLocalDate())
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device
						.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DeviceOrderedDto> getDevicesOrdered() {
		return deviceRepository.getDevicesOrdered()
			.stream()
			.map(device -> DeviceOrderedDto.builder()
				.deviceId(device.getId())
				.buyingDate(device.getBuyingDate())
				.model(modelDetailsService.getModel(device.getModelId()))
				.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
				.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
				.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
				.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<Integer> findDeviceIdsBySerialNumber(String serialNumber) {
		return deviceRepository.findDeviceIdsBySerialNumber(serialNumber);
	}

	@Transactional(readOnly = true)
	public List<Integer> findDeviceIdsByUrl(String url) {
		return deviceRepository.findDeviceIdsWithUrl(url);
	}
}