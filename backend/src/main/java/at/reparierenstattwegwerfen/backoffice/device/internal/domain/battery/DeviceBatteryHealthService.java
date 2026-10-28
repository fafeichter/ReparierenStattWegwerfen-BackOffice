package at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos.BatteryHealthDto;
import at.reparierenstattwegwerfen.backoffice.shared.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Fabian Feichter
 * @since 11.10.2026
 */
@Service
@RequiredArgsConstructor
public class DeviceBatteryHealthService {

	private final DeviceRepository deviceRepository;
	private final DeviceBatteryStatusRepository deviceBatteryStatusRepository;
	private final ApplicationEventPublisher events;

	@Transactional
	public void updateBatteryHealth(Integer deviceId, BatteryHealthDto newDeviceBaseBattery, UserDetails actor) {
		Device device = deviceRepository.getById(deviceId);

		if (!Objects.equals(newDeviceBaseBattery.getCycleCount(), device.getBatteryCycleCount()) ||
			!Objects.equals(newDeviceBaseBattery.getMaximumCapacity(), device.getBatteryMaximumCapacity())) {

			device.setBatteryMaximumCapacity(newDeviceBaseBattery.getMaximumCapacity());
			device.setBatteryCycleCount(newDeviceBaseBattery.getCycleCount());

			boolean batteryStatusAutomaticallySetOrUpdated =
				(newDeviceBaseBattery.determineStatusId() != null && device.getBatteryStatus() == null) ||
					(newDeviceBaseBattery.determineStatusId() == null && device.getBatteryStatus() != null) ||
					(!Objects.equals(newDeviceBaseBattery.determineStatusId(), device.getBatteryStatus().getId()));

			if (batteryStatusAutomaticallySetOrUpdated) {
				device.setBatteryStatus(newDeviceBaseBattery.determineStatusId() != null ?
					deviceBatteryStatusRepository.getById(newDeviceBaseBattery.determineStatusId()) : null);
			}

			deviceRepository.save(device);

			DeviceBatteryHealthChanged batteryHealthChangedEvent = DeviceBatteryHealthChanged.builder()
				.source(this)
				.actor(actor)
				.deviceId(deviceId)
				.maximumCapacity(newDeviceBaseBattery.getMaximumCapacity())
				.cycleCount(newDeviceBaseBattery.getCycleCount())
				.build();
			events.publishEvent(batteryHealthChangedEvent);

			if (batteryStatusAutomaticallySetOrUpdated) {
				DeviceBatteryStatusChanged deviceBatteryStatusChangedEvent = DeviceBatteryStatusChanged.builder()
					.source(this)
					.actor(SystemUser.get())
					.deviceId(deviceId)
					.newBatteryStatusId(newDeviceBaseBattery.determineStatusId())
					.build();
				events.publishEvent(deviceBatteryStatusChangedEvent);
			}
		}
	}
}