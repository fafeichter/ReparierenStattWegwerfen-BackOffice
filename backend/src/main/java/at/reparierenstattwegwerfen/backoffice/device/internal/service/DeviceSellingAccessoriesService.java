package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class DeviceSellingAccessoriesService {

	private final DeviceRepository deviceRepository;

	@Transactional(readOnly = true)
	public DeviceSellingAccessoriesDto load(Integer deviceId) {
		Device device = deviceRepository.getReferenceById(deviceId);

		return DeviceSellingAccessoriesDto.builder()
			.charger(device.getSellingAccessoryCharger())
			.chargingCable(device.getSellingAccessoryChargingCable())
			.originalPackaging(device.getSellingAccessoryOriginalPackaging())
			.build();
	}

	@Transactional
	public void update(Integer deviceId, DeviceSellingAccessoriesFormDto accessoriesFormDto) {
		Device device = deviceRepository.getReferenceById(deviceId);

		device.setSellingAccessoryCharger(accessoriesFormDto.getCharger());
		device.setSellingAccessoryChargingCable(accessoriesFormDto.getChargingCable());
		device.setSellingAccessoryOriginalPackaging(accessoriesFormDto.getOriginalPackaging());

		deviceRepository.save(device);
	}
}