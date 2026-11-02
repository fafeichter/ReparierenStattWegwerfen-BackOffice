package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts.DeviceInstalledSparePart;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceInstalledSparePartEntity;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper
public interface DeviceInstalledSparePartPersistenceMapper {

	DeviceInstalledSparePart toDomain(DeviceInstalledSparePartEntity entity);

	DeviceInstalledSparePartEntity toEntity(DeviceInstalledSparePart domain);
}