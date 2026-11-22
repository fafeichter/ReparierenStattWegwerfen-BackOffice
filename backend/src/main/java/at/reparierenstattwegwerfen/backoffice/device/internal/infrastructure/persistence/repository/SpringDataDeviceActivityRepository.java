package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceActivityEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Repository
public interface SpringDataDeviceActivityRepository extends JpaRepository<DeviceActivityEntity, Integer> {

	@Query("""
		from DeviceActivityEntity a
		left join fetch a.activityType
		where a.deviceId = :deviceId
		order by a.date desc
		""")
	List<DeviceActivityEntity> getByIdWithRelations(Integer deviceId);

	@Query("""
		select max(a.date)
		from DeviceActivityEntity a
		where a.deviceId = :deviceId
		""")
	LocalDateTime getLastActivityDateForDevice(Integer deviceId);

	@Query("""
		from DeviceActivityEntity a
		left join fetch a.activityType
		where a.date = (
			select max(latestDay.date)
			from DeviceActivityEntity latestDay
			where latestDay.deviceId = a.deviceId
		)
		and a.id = (
			select max(tieBreak.id)
			from DeviceActivityEntity tieBreak
			where tieBreak.deviceId = a.deviceId
			and tieBreak.date = a.date
		)
		order by a.date desc, a.id desc
		""")
	List<DeviceActivityEntity> getLastActivities(Pageable page);
}