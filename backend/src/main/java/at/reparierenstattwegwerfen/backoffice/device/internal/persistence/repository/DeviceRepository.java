package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	List<Device> findBySellerBusinessPartnerId(Integer sellerBusinessPartnerId);

	List<Device> findByBuyerBusinessPartnerId(Integer sellerBusinessPartnerId);
}