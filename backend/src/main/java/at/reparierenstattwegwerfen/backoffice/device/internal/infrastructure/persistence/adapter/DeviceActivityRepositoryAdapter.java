package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceActivityPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceActivityRepositoryAdapter implements DeviceActivityRepository {

	private final SpringDataDeviceActivityRepository jpaRepository;
	private final DeviceActivityPersistenceMapper mapper;

	@Override
	public Optional<DeviceActivity> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public void save(DeviceActivity activity) {
		var entity = mapper.toEntity(activity);
		jpaRepository.save(entity);
	}

	@Override
	public List<DeviceActivity> getByIdWithRelations(Integer deviceId) {
		return jpaRepository.getByIdWithRelations(deviceId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public LocalDateTime getLastActivityDateForDevice(Integer deviceId) {
		return jpaRepository.getLastActivityDateForDevice(deviceId);
	}
}