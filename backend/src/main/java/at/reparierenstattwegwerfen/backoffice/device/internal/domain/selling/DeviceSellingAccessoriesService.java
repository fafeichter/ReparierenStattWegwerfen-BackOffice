package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 08.07.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceSellingAccessoriesService {

	private final DeviceRepository deviceRepository;

	@Transactional(readOnly = true)
	public DeviceSellingAccessoriesDto load(Integer deviceId) {
		Device device = deviceRepository.getById(deviceId);

		return DeviceSellingAccessoriesDto.builder()
			.charger(device.getSellingAccessoryCharger())
			.chargingCable(device.getSellingAccessoryChargingCable())
			.originalPackaging(device.getSellingAccessoryOriginalPackaging())
			.build();
	}

	@Transactional
	public void update(Integer deviceId, DeviceSellingAccessoriesFormDto accessoriesFormDto) {
		Device device = deviceRepository.getById(deviceId);

		device.setSellingAccessoryCharger(accessoriesFormDto.getCharger());
		device.setSellingAccessoryChargingCable(accessoriesFormDto.getChargingCable());
		device.setSellingAccessoryOriginalPackaging(accessoriesFormDto.getOriginalPackaging());

		deviceRepository.save(device);
	}
}