package at.reparierenstattwegwerfen.backoffice.device.internal.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceRepository {

	Optional<Device> findById(Integer id);

	Device getReferenceById(Integer id);

	Device save(Device device);

	List<Device> findBySellerBusinessPartnerId(Integer sellerBusinessPartnerId);

	List<Device> findAllByStatusIdIn(List<Integer> statusIds);

	List<Device> findByBuyerBusinessPartnerId(Integer buyerBusinessPartnerId);

	void archiveSoldDevices(List<Integer> deviceIds);

	List<Integer> findDevicesToArchive();

	Page<Device> search(Pageable page, Boolean includeInactiveDevices);

	Device getByIdWithRelations(Integer id);
}