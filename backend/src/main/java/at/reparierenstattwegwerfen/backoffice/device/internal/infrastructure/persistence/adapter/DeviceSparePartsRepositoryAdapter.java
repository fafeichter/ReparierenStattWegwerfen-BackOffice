package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSparePartsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceSparePartsPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceSparePartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceSparePartsRepositoryAdapter implements DeviceSparePartsRepository {

	private final SpringDataDeviceSparePartsRepository jpaRepository;
	private final DeviceSparePartsPersistenceMapper mapper;

	@Override
	public Optional<DeviceSpareParts> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public DeviceSpareParts getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public DeviceSpareParts save(DeviceSpareParts sparePart) {
		var entity = mapper.toEntity(sparePart);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public void deleteById(Integer id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public List<DeviceSpareParts> getSparePartsForDevice(Integer deviceId) {
		return jpaRepository.getSparePartsForDevice(deviceId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}