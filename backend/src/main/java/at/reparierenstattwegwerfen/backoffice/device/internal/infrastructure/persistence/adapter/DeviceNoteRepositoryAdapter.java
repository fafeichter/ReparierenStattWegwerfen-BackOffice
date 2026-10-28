package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes.DeviceNote;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes.DeviceNoteRepository;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper.DeviceNotePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository.SpringDataDeviceNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Repository
@RequiredArgsConstructor
public class DeviceNoteRepositoryAdapter implements DeviceNoteRepository {

	private final SpringDataDeviceNoteRepository jpaRepository;
	private final DeviceNotePersistenceMapper mapper;

	@Override
	public DeviceNote getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public DeviceNote save(DeviceNote note) {
		var entity = mapper.toEntity(note);
		var saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public void deleteById(Integer id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public List<DeviceNote> getNotesForDevice(Integer deviceId) {
		return jpaRepository.getNotesForDevice(deviceId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}