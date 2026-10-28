package at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceBatteryStatusRepository {

	DeviceBatteryStatus getById(Integer id);

	List<DeviceBatteryStatus> getAllStatus();
}