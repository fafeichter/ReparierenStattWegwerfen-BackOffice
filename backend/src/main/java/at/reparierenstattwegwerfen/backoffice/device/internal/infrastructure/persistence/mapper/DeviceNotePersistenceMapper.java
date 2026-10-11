package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes.DeviceNote;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceNoteEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper
public interface DeviceNotePersistenceMapper {

	DeviceNote toDomain(DeviceNoteEntity entity);

	DeviceNoteEntity toEntity(DeviceNote domain);
}