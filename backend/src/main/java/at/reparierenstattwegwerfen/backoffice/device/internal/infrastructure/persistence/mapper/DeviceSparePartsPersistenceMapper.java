package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceSpareParts;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceSparePartsEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceSparePartsPersistenceMapper {

	DeviceSpareParts toDomain(DeviceSparePartsEntity entity);

	DeviceSparePartsEntity toEntity(DeviceSpareParts domain);
}