package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceBatteryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceBatteryStatusRepository extends JpaRepository<DeviceBatteryStatus, Integer> {

	@Query("""
		SELECT n FROM DeviceBatteryStatus n
		ORDER BY n.sortOrder ASC
		""")
	List<DeviceBatteryStatus> getAllStatus();
}