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
		from DeviceTagEntity t
		join DeviceAddedTagEntity d on d.deviceTag.id = t.id
		where d.device.id = :deviceId
		order by t.sortOrder
		""")
	List<DeviceTagEntity> getAddedTagsForDevice(Integer deviceId);

	@Query("""
		from DeviceTagEntity t
		join DeviceTagAvailableModelSeriesEntity ts on ts.tag.id = t.id
		where ts.modelSeriesId = :modelSeriesId
		and not exists (
			select 1
			from DeviceAddedTagEntity dts
			where dts.deviceTag.id = t.id
			and dts.device.id = :deviceId
		)
		order by t.sortOrder
		""")
	List<DeviceTagEntity> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId);
}