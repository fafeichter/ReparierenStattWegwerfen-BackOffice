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
	public DeviceActivity save(DeviceActivity activity) {
		var entity = mapper.toEntity(activity);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
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