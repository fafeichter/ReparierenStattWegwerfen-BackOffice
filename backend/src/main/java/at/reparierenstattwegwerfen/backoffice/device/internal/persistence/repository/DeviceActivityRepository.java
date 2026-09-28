package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, Integer> {

	@Query("""
		SELECT a FROM DeviceActivity a
		LEFT JOIN FETCH a.activityType
		WHERE a.deviceId = :deviceId
		ORDER BY a.date DESC
		""")
	List<DeviceActivity> getByIdWithRelations(Integer deviceId);

	@Query("SELECT MAX(a.date) FROM DeviceActivity a WHERE a.deviceId = :deviceId")
	LocalDateTime getLastActivityDateForDevice(Integer deviceId);
}