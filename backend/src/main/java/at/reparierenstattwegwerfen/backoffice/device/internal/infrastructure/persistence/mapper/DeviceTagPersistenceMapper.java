package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceAddedTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceAddedTagEntity;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceTagEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper(uses = DevicePersistenceMapper.class)
public interface DeviceTagPersistenceMapper {

	DeviceTag toDomain(DeviceTagEntity entity);

	DeviceTagEntity toEntity(DeviceTag domain);

	DeviceAddedTag toDomain(DeviceAddedTagEntity entity);

	DeviceAddedTagEntity toEntity(DeviceAddedTag domain);
}