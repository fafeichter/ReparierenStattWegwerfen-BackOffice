package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceTagPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	public DeviceTag getById(Integer id) {
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