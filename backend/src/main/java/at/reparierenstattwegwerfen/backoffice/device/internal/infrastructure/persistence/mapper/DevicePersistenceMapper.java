package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.battery.DeviceBatteryStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.buying.DeviceOnlineMarketplace;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade.DeviceGrade;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.status.DeviceStatusClassification;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.*;
import org.mapstruct.Mapper;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
@Mapper
public interface DevicePersistenceMapper {

	Device toDomain(DeviceEntity entity);

	DeviceEntity toEntity(Device domain);

	DeviceStatus toDomain(DeviceStatusEntity entity);

	DeviceStatusEntity toEntity(DeviceStatus domain);

	DeviceStatusClassification toDomain(DeviceStatusClassificationEntity entity);

	DeviceStatusClassificationEntity toEntity(DeviceStatusClassification domain);

	DeviceGrade toDomain(DeviceGradeEntity entity);

	DeviceGradeEntity toEntity(DeviceGrade domain);

	DeviceBatteryStatus toDomain(DeviceBatteryStatusEntity entity);

	DeviceBatteryStatusEntity toEntity(DeviceBatteryStatus domain);

	DeviceOnlineMarketplace toDomain(DeviceOnlineMarketplaceEntity entity);

	DeviceOnlineMarketplaceEntity toEntity(DeviceOnlineMarketplace domain);
}