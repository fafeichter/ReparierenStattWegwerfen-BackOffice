package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityType;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityTypeRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceActivityPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceActivityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceActivityTypeRepositoryAdapter implements DeviceActivityTypeRepository {

	private final SpringDataDeviceActivityTypeRepository jpaRepository;
	private final DeviceActivityPersistenceMapper mapper;

	@Override
	public Optional<DeviceActivityType> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public DeviceActivityType getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}
}