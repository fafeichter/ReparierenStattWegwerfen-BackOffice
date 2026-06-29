package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceSparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceSparePartRepository extends JpaRepository<DeviceSparePart, Integer> {

	@Query("""
		SELECT n FROM DeviceSparePart n
		WHERE n.deviceId = :deviceId
		ORDER BY n.timestamp DESC
		""")
	List<DeviceSparePart> getSparePartsForDevice(Integer deviceId);
}