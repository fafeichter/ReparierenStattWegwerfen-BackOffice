package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceTagRepository extends JpaRepository<DeviceTagEntity, Integer> {

	@Query("""
		SELECT t FROM DeviceTagEntity t
		JOIN DeviceTagsEntity d ON d.deviceTag.id = t.id
		WHERE d.device.id = :deviceId
		ORDER BY t.sortOrder
		""")
	List<DeviceTagEntity> getTagsForDevice(Integer deviceId);

	@Query("""
		SELECT t
		FROM DeviceTagEntity t
		JOIN DeviceTagAvailableModelSeriesEntity ts
		  ON t.id = ts.tag.id
		WHERE ts.modelSeriesId = :modelSeriesId
		AND NOT EXISTS (
		  SELECT 1
		  FROM DeviceTagsEntity dts
		  WHERE dts.deviceTag.id = t.id
		  AND dts.device.id = :deviceId
		)
		ORDER BY t.sortOrder
		""")
	List<DeviceTagEntity> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId);
}