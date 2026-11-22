package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceBaseDetailsRepository extends JpaRepository<DeviceEntity, Integer> {

	@Query("""
		from DeviceEntity d
		left join fetch d.status
		left join fetch d.grade
		left join fetch d.batteryStatus
		where d.id = :id
		""")
	DeviceEntity getByIdWithRelations(Integer id);
}