package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatusRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DevicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceBatteryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceBatteryStatusRepositoryAdapter implements DeviceBatteryStatusRepository {

	private final SpringDataDeviceBatteryStatusRepository jpaRepository;
	private final DevicePersistenceMapper mapper;

	@Override
	public DeviceBatteryStatus getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public List<DeviceBatteryStatus> getAllStatus() {
		return jpaRepository.getAllStatus()
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}