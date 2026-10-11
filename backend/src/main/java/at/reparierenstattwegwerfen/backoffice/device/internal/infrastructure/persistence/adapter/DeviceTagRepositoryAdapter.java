package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceTagPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceTagRepository;
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
public class DeviceTagRepositoryAdapter implements DeviceTagRepository {

	private final SpringDataDeviceTagRepository jpaRepository;
	private final DeviceTagPersistenceMapper mapper;

	@Override
	public Optional<DeviceTag> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public DeviceTag getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public List<DeviceTag> getTagsForDevice(Integer deviceId) {
		return jpaRepository.getTagsForDevice(deviceId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<DeviceTag> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId) {
		return jpaRepository.getAvailableTagsForDevice(deviceId, modelSeriesId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}