package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceBaseDetailsRepository extends JpaRepository<DeviceEntity, Integer> {

	@Query("SELECT d FROM DeviceEntity d " +
		"LEFT JOIN FETCH d.status " +
		"LEFT JOIN FETCH d.grade " +
		"LEFT JOIN FETCH d.batteryStatus " +
		"WHERE d.id = :id")
	DeviceEntity getByIdWithRelations(@Param("id") Integer id);
}