package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceBaseDetailsRepository extends JpaRepository<Device, Integer> {

	@Query("SELECT d FROM Device d " +
		"LEFT JOIN FETCH d.status " +
		"LEFT JOIN FETCH d.grade " +
		"LEFT JOIN FETCH d.sellingDeviceOnlineMarketplace " +
		"LEFT JOIN FETCH d.batteryStatus " +
		"WHERE d.id = :id")
	Device getByIdWithRelations(@Param("id") Integer id);
}