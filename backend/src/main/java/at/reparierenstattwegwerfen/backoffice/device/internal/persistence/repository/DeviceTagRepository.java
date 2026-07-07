package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceTagRepository extends JpaRepository<DeviceTag, Integer> {

	@Query("""
		SELECT t FROM DeviceTag t
		JOIN DeviceTags d ON d.deviceTag.id = t.id
		WHERE d.device.id = :deviceId
		ORDER BY t.sortOrder
		""")
	List<DeviceTag> getTagsForDevice(Integer deviceId);
}