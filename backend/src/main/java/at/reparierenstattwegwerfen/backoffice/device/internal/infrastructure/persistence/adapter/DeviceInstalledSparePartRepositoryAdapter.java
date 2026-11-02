package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceInstalledSparePart;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceInstalledSparePartRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceInstalledSparePartPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceInstalledSparePartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceInstalledSparePartRepositoryAdapter implements DeviceInstalledSparePartRepository {

	private final SpringDataDeviceInstalledSparePartRepository jpaRepository;
	private final DeviceInstalledSparePartPersistenceMapper mapper;

	@Override
	public DeviceInstalledSparePart getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public DeviceInstalledSparePart save(DeviceInstalledSparePart sparePart) {
		var entity = mapper.toEntity(sparePart);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public void deleteById(Integer id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public List<DeviceInstalledSparePart> getSparePartsForDevice(Integer deviceId) {
		return jpaRepository.getSparePartsForDevice(deviceId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}