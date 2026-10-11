package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes.DeviceNote;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceNoteEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceNotePersistenceMapper {

	DeviceNote toDomain(DeviceNoteEntity entity);

	DeviceNoteEntity toEntity(DeviceNote domain);
}