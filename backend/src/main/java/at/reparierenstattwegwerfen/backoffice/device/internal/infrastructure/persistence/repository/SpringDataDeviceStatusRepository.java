package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataDeviceStatusRepository extends JpaRepository<DeviceStatusEntity, Integer> {

	@Query("""
		from DeviceStatusEntity n
		where n.deviceStatusClassification.id in (1, 2)
		order by n.sortOrder
		""")
	List<DeviceStatusEntity> getAllNonSystemStatus();
}