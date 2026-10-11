package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags.DeviceTags;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceTagEntity;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceTagsEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper(componentModel = "spring", uses = DevicePersistenceMapper.class)
public interface DeviceTagPersistenceMapper {

	DeviceTag toDomain(DeviceTagEntity entity);

	DeviceTagEntity toEntity(DeviceTag domain);

	DeviceTags toDomain(DeviceTagsEntity entity);

	DeviceTagsEntity toEntity(DeviceTags domain);
}