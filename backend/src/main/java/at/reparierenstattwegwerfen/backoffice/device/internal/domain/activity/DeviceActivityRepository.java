package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceActivityRepository {

	void save(DeviceActivity activity);

	List<DeviceActivity> getByIdWithRelations(Integer deviceId);

	LocalDateTime getLastActivityDateForDevice(Integer deviceId);

	List<DeviceActivity> getLastActivities(Pageable page);
}