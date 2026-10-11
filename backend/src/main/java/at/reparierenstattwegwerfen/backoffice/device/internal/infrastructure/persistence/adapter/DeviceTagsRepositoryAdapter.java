package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTags;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTagsRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceTagPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceTagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeviceTagsRepositoryAdapter implements DeviceTagsRepository {

	private final SpringDataDeviceTagsRepository jpaRepository;
	private final DeviceTagPersistenceMapper mapper;

	@Override
	public void deleteByDeviceTagId(Integer deviceTagId) {
		jpaRepository.deleteByDeviceTagId(deviceTagId);
	}

	@Override
	public DeviceTags save(DeviceTags deviceTags) {
		var entity = mapper.toEntity(deviceTags);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}
}