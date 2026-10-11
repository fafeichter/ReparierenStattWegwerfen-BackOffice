package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceBatteryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataDeviceBatteryStatusRepository extends JpaRepository<DeviceBatteryStatusEntity, Integer> {

	@Query("""
		SELECT n FROM DeviceBatteryStatusEntity n
		ORDER BY n.sortOrder ASC
		""")
	List<DeviceBatteryStatusEntity> getAllStatus();
}