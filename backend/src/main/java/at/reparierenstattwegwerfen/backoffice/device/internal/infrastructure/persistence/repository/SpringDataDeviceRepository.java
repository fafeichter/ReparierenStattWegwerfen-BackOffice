package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataDeviceRepository extends JpaRepository<DeviceEntity, Integer> {

	List<DeviceEntity> findBySellerBusinessPartnerId(Integer sellerBusinessPartnerId);

	@Query("""
		from DeviceEntity d
		where d.status.id = 1
		order by d.statusDate, d.id
		""")
	List<DeviceEntity> getDevicesOrdered();

	@Query("""
		from DeviceEntity d
		where d.status.id in (2, 3, 4)
		order by d.status.sortOrder, d.statusDate
		""")
	List<DeviceEntity> getDevicesToFinish();

	@Query("""
		from DeviceEntity d
		where d.status.id = 5
		order by d.statusDate, d.id
		""")
	List<DeviceEntity> getDevicesOfferedForSale();

	List<DeviceEntity> findByBuyerBusinessPartnerId(Integer sellerBusinessPartnerId);

	@Query("""
		update DeviceEntity d
		set d.status.id = 8
		where d.id in (:deviceIds)
		""")
	@Modifying
	void archiveSoldDevices(List<Integer> deviceIds);

	@Query("""
		select d.id
		from DeviceEntity d
		where d.status.id = 6
		and d.sellingDate < current_date - 365 day
		""")
	List<Integer> findDevicesToArchive();

	@Query("""
		from DeviceEntity d
		where :includeInactiveDevices = true
		or d.status.id in (
			select s.id
			from DeviceStatusEntity s
			where s.isEndstatus is false
		)
		order by d.id desc
		""")
	Page<DeviceEntity> search(Pageable page, Boolean includeInactiveDevices);

	@Query("""
		select d.id
		from DeviceEntity d
		where lower(d.serialNumber) like lower(concat('%', :serialNumber, '%'))
		""")
	List<Integer> findDeviceIdsBySerialNumber(String serialNumber);

	@Query("""
		select d.id
		from DeviceEntity d
		where d.url = :url
		""")
	List<Integer> findDeviceIdsByUrl(String url);
}