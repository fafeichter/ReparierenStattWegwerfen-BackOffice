package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
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
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	List<Device> findBySellerBusinessPartnerId(Integer sellerBusinessPartnerId);

	List<Device> findAllByStatusIdIn(List<Integer> statusIds);

	List<Device> findByBuyerBusinessPartnerId(Integer sellerBusinessPartnerId);

	@Query("""
		update Device d
		set d.status.id = 8
		where d.id in (:deviceIds)
		""")
	@Modifying
	void archiveSoldDevices(List<Integer> deviceIds);

	@Query("""
		select d.id
		from Device d
		where d.status.id = 6
				and d.sellingDate < CURRENT_DATE - 365 day
		""")
	List<Integer> findDevicesToArchive();
}