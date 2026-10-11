package at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery;

import java.util.List;
import java.util.Optional;

public interface DeviceBatteryStatusRepository {

	Optional<DeviceBatteryStatus> findById(Integer id);

	DeviceBatteryStatus getReferenceById(Integer id);

	List<DeviceBatteryStatus> getAllStatus();
}