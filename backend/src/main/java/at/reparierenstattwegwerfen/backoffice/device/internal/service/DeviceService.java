package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.controller.DeviceOfferedForSaleDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.controller.DeviceOrderedDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.controller.DeviceToFinishDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceService {

	private final DeviceRepository deviceRepository;
	private final ModelDetailsService modelDetailsService;

	public List<DeviceOrderedDto> getDevicesOrdered() {
		return deviceRepository.findAllByStatusIdIn(List.of(1)).stream().map(device -> DeviceOrderedDto.builder()
				.deviceId(device.getId())
				.buyingDate(device.getBuyingDate())
				.model(modelDetailsService.getModel(device.getModelId()))
				.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
				.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
				.storage(modelDetailsService.getStorage(device.getModelStorageId()))
				.build())
			.toList();
	}

	public List<DeviceToFinishDto> getDevicesToFinish() {
		return deviceRepository.findAllByStatusIdIn(List.of(2, 3, 4))
			.stream()
			.map(device ->
				DeviceToFinishDto.builder()
					.deviceId(device.getId())
					.status(NamedIdDto.from(device.getStatus()))
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}

	public List<DeviceOfferedForSaleDto> getDevicesOfferedForSale() {
		return deviceRepository.findAllByStatusIdIn(List.of(5))
			.stream()
			.map(device ->
				DeviceOfferedForSaleDto.builder()
					.deviceId(device.getId())
					.statusDate(device.getStatusDate().toLocalDate())
					.model(modelDetailsService.getModel(device.getModelId()))
					.appleSilicon(modelDetailsService.getAppleSilicon(device.getModelAppleSiliconId()))
					.unifiedMemory(modelDetailsService.getUnifiedMemory(device.getModelAppleSiliconUnifiedMemoryId()))
					.storage(modelDetailsService.getStorage(device.getModelStorageId()))
					.build())
			.toList();
	}
}