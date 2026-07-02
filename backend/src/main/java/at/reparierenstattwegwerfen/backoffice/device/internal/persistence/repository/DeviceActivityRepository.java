package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, Integer> {

	@Query("""
		SELECT a FROM DeviceActivity a
		LEFT JOIN FETCH a.activityType
		WHERE a.device.id = :deviceId
		ORDER BY a.date DESC
		""")
	List<DeviceActivity> getByIdWithRelations(Integer deviceId);
}