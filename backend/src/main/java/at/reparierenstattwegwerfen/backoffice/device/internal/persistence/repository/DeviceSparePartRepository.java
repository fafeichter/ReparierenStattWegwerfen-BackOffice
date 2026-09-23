package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceSpareParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface DeviceSparePartRepository extends JpaRepository<DeviceSpareParts, Integer> {

	@Query("""
		SELECT n
		FROM DeviceSpareParts n
		WHERE n.deviceId = :deviceId
		ORDER BY n.date DESC
		""")
	List<DeviceSpareParts> getSparePartsForDevice(Integer deviceId);
}