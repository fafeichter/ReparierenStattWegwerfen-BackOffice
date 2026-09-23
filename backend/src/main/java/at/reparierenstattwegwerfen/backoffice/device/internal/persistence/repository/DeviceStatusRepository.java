package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Integer> {

	@Query("""
		SELECT n FROM DeviceStatus n
		WHERE n.deviceStatusClassification.id IN (1, 2)
		ORDER BY n.sortOrder
		""")
	List<DeviceStatus> getAllNonSystemStatus();
}