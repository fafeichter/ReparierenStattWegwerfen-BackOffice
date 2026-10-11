package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceSparePartsEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper
public interface DeviceSparePartsPersistenceMapper {

	DeviceSpareParts toDomain(DeviceSparePartsEntity entity);

	DeviceSparePartsEntity toEntity(DeviceSpareParts domain);
}