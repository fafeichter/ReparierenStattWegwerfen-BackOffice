package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceActivityEntity;
import org.springframework.data.domain.Pageable;
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
		FROM DeviceActivityEntity a
		LEFT JOIN FETCH a.activityType
		WHERE a.deviceId = :deviceId
		ORDER BY a.date DESC
		""")
	List<DeviceActivityEntity> getByIdWithRelations(Integer deviceId);

	@Query("""
		SELECT MAX(a.date)
		FROM DeviceActivityEntity a
		WHERE a.deviceId = :deviceId
		""")
	LocalDateTime getLastActivityDateForDevice(Integer deviceId);

	@Query("""
		FROM DeviceActivityEntity a
		LEFT JOIN FETCH a.activityType
		WHERE a.date = (
		    SELECT MAX(latestDay.date)
		    FROM DeviceActivityEntity latestDay
		    WHERE latestDay.deviceId = a.deviceId
		)
		AND a.id = (
		    SELECT MAX(tieBreak.id)
		    FROM DeviceActivityEntity tieBreak
		    WHERE tieBreak.deviceId = a.deviceId
		      AND tieBreak.date = a.date
		)
		ORDER BY a.date DESC, a.id DESC
		""")
	List<DeviceActivityEntity> getLastActivities(Pageable page);
}