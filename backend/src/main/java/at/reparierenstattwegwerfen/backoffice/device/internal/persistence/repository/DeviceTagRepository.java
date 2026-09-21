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

	@Query("""
		SELECT t
		FROM DeviceTag t
		JOIN DeviceTagAvailableModelSeries ts
		  ON t.id = ts.tag.id
		WHERE ts.modelSeriesId = :modelSeriesId
		AND NOT EXISTS (
		  SELECT 1
		  FROM DeviceTags dts
		  WHERE dts.device.id = t.id
		  AND dts.device.id = :deviceId
		)
		""")
	List<DeviceTag> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId);
}