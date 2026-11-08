package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesDto;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.DeviceSellingAccessoriesFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
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
	private final ApplicationEventPublisher events;

	@Transactional(readOnly = true)
	public DeviceSellingAccessoriesDto getSellingAccessoriesForDevice(Integer deviceId) {
		Device device = deviceRepository.getById(deviceId);

		return DeviceSellingAccessoriesDto.builder()
			.charger(device.getSellingAccessoryCharger())
			.chargingCable(device.getSellingAccessoryChargingCable())
			.originalPackaging(device.getSellingAccessoryOriginalPackaging())
			.build();
	}

	@Transactional
	public void updateSellingAccessories(Integer deviceId, DeviceSellingAccessoriesFormDto accessoriesFormDto,
										 UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		boolean accessoriesChanged = device.getSellingAccessoryCharger() != accessoriesFormDto.getCharger() ||
			device.getSellingAccessoryChargingCable() != accessoriesFormDto.getChargingCable() ||
			device.getSellingAccessoryOriginalPackaging() != accessoriesFormDto.getOriginalPackaging();

		if (accessoriesChanged) {
			device.setSellingAccessoryCharger(accessoriesFormDto.getCharger());
			device.setSellingAccessoryChargingCable(accessoriesFormDto.getChargingCable());
			device.setSellingAccessoryOriginalPackaging(accessoriesFormDto.getOriginalPackaging());

			deviceRepository.save(device);

			DeviceAccessoriesChanged deviceAccessoriesChangedEvent = DeviceAccessoriesChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.charger(accessoriesFormDto.getCharger())
				.chargingCable(accessoriesFormDto.getChargingCable())
				.originalPackaging(accessoriesFormDto.getOriginalPackaging())
				.build();
			events.publishEvent(deviceAccessoriesChangedEvent);
		}
	}
}