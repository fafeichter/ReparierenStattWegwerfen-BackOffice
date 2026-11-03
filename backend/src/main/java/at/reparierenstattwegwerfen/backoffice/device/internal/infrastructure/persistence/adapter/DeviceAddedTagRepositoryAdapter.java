package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceAddedTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceAddedTagRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceTagPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceAddedTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceAddedTagRepositoryAdapter implements DeviceAddedTagRepository {

	private final SpringDataDeviceAddedTagRepository jpaRepository;
	private final DeviceTagPersistenceMapper mapper;

	@Override
	public void removeTagFromDevice(Integer deviceTagId) {
		jpaRepository.deleteByDeviceTagId(deviceTagId);
	}

	@Override
	public void save(DeviceAddedTag deviceAddedTag) {
		var entity = mapper.toEntity(deviceAddedTag);
		jpaRepository.save(entity);
	}
}