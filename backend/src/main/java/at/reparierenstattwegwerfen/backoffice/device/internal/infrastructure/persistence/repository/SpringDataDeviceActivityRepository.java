package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceActivityEntity;
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
public interface SpringDataDeviceActivityRepository extends JpaRepository<DeviceActivityEntity, Integer> {

	@Query("""
		SELECT a FROM DeviceActivityEntity a
		LEFT JOIN FETCH a.activityType
		WHERE a.deviceId = :deviceId
		ORDER BY a.date DESC
		""")
	List<DeviceActivityEntity> getByIdWithRelations(Integer deviceId);

	@Query("SELECT MAX(a.date) FROM DeviceActivityEntity a WHERE a.deviceId = :deviceId")
	LocalDateTime getLastActivityDateForDevice(Integer deviceId);
}