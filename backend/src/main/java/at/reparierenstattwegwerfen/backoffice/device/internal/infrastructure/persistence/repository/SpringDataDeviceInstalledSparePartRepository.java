package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceInstalledSparePartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceInstalledSparePartRepository extends JpaRepository<DeviceInstalledSparePartEntity, Integer> {

	@Query("""
		SELECT n
		FROM DeviceInstalledSparePartEntity n
		WHERE n.deviceId = :deviceId
		ORDER BY n.date
		""")
	List<DeviceInstalledSparePartEntity> getSparePartsForDevice(Integer deviceId);
}