package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DevicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeviceStatusRepositoryAdapter implements DeviceStatusRepository {

	private final SpringDataDeviceStatusRepository jpaRepository;
	private final DevicePersistenceMapper mapper;

	@Override
	public Optional<DeviceStatus> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public DeviceStatus getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public List<DeviceStatus> getAllNonSystemStatus() {
		return jpaRepository.getAllNonSystemStatus()
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}