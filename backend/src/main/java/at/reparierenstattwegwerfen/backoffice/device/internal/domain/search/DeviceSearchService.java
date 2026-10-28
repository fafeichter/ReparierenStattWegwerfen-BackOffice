package at.reparierenstattwegwerfen.backoffice.device.internal.domain.search;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityService;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOfferedForSaleDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceOrderedDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceToFinishDto;
import at.reparierenstattwegwerfen.backoffice.model.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	private final ModelColorDetailsService modelColorDetailsService;
	private final ModelStorageDetailsService modelStorageDetailsService;
	private final DeviceActivityService deviceActivityService;

	@Transactional(readOnly = true)
	public Page<DeviceDto> search(Integer pageNumber, Integer pageSize, Boolean includeInactiveDevices) {
		PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id"));

		return deviceRepository.search(page, includeInactiveDevices)
			.map(device -> DeviceDto.builder()
				.deviceId(device.getId())
				.status(NamedIdDto.from(device.getStatus()))
				.model(modelDetailsService.getModel(device.getModelId()))
				.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
				.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
				.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
				.lastActivity(deviceActivityService.getLastActivityDateForDevice(device.getId()))
				.build());
	}

	@Transactional(readOnly = true)
	public List<DeviceToFinishDto> getDevicesToFinish() {
		return deviceRepository.findAllByStatusIdIn(List.of(2, 3, 4))
			.stream()
			.map(device ->
				DeviceToFinishDto.builder()
					.deviceId(device.getId())
					.status(NamedIdDto.from(device.getStatus()))
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DeviceOfferedForSaleDto> getDevicesOfferedForSale() {
		return deviceRepository.findAllByStatusIdIn(List.of(5))
			.stream()
			.map(device ->
				DeviceOfferedForSaleDto.builder()
					.deviceId(device.getId())
					.statusDate(device.getStatusDate().toLocalDate())
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelAppleSiliconDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelUnifiedMemoryDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelStorageDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	@Transactional(readOnly = true)
	public List<DeviceOrderedDto> getDevicesOrdered() {
		return deviceRepository.findAllByStatusIdIn(List.of(1))
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
	public Integer findDeviceIdBySerialNumber(String serialNumber) {
		return deviceRepository.findDeviceIdBySerialNumber(serialNumber);
	}
}