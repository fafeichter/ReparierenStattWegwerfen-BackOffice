package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceSparePartsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceSparePartsRepository extends JpaRepository<DeviceSparePartsEntity, Integer> {

	@Query("""
		SELECT n
		FROM DeviceSparePartsEntity n
		WHERE n.deviceId = :deviceId
		ORDER BY n.date
		""")
	List<DeviceSparePartsEntity> getSparePartsForDevice(Integer deviceId);
}