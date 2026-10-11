package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGrade;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGradeRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DevicePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceGradeRepository;
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
public class DeviceGradeRepositoryAdapter implements DeviceGradeRepository {

	private final SpringDataDeviceGradeRepository jpaRepository;
	private final DevicePersistenceMapper mapper;

	@Override
	public Optional<DeviceGrade> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public DeviceGrade getReferenceById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public List<DeviceGrade> getAllGrades() {
		return jpaRepository.getAllGrades()
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}