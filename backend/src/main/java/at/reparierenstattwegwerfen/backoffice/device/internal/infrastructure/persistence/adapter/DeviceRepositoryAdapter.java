package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.DeviceRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DevicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceBaseDetailsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceRepositoryAdapter implements DeviceRepository {

	private final SpringDataDeviceRepository jpaRepository;
	private final SpringDataDeviceBaseDetailsRepository baseDetailsRepository;
	private final DevicePersistenceMapper mapper;

	@Override
	public Device getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public Device save(Device device) {
		var entity = mapper.toEntity(device);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public List<Device> findBySellerBusinessPartnerId(Integer sellerBusinessPartnerId) {
		return jpaRepository.findBySellerBusinessPartnerId(sellerBusinessPartnerId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Device> findAllByStatusIdIn(List<Integer> statusIds) {
		return jpaRepository.findAllByStatusIdIn(statusIds)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Device> findByBuyerBusinessPartnerId(Integer buyerBusinessPartnerId) {
		return jpaRepository.findByBuyerBusinessPartnerId(buyerBusinessPartnerId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public void archiveSoldDevices(List<Integer> deviceIds) {
		jpaRepository.archiveSoldDevices(deviceIds);
	}

	@Override
	public List<Integer> findDevicesToArchive() {
		return jpaRepository.findDevicesToArchive();
	}

	@Override
	public Page<Device> search(Pageable page, Boolean includeInactiveDevices) {
		return jpaRepository.search(page, includeInactiveDevices)
			.map(mapper::toDomain);
	}

	@Override
	public Device getByIdWithRelations(Integer id) {
		var entity = baseDetailsRepository.getByIdWithRelations(id);
		return mapper.toDomain(entity);
	}

	@Override
	public Integer findDeviceIdBySerialNumber(String serialNumber) {
		String serialNumberSearchPattern = "%" + serialNumber.trim() + "%";
		return jpaRepository.findDeviceIdBySerialNumberLike(serialNumberSearchPattern);
	}
}