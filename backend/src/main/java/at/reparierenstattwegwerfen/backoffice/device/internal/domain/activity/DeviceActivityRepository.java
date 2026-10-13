package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceActivityRepository {

	Optional<DeviceActivity> findById(Integer id);

	void save(DeviceActivity activity);

	List<DeviceActivity> getByIdWithRelations(Integer deviceId);

	LocalDateTime getLastActivityDateForDevice(Integer deviceId);
}