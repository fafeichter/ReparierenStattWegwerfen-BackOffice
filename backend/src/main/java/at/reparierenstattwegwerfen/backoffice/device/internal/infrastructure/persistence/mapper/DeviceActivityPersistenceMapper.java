package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivity;
import at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity.DeviceActivityType;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceActivityEntity;
import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceActivityTypeEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceActivityPersistenceMapper {

	DeviceActivityType toDomain(DeviceActivityTypeEntity entity);

	DeviceActivityTypeEntity toEntity(DeviceActivityType domain);

	DeviceActivity toDomain(DeviceActivityEntity entity);

	DeviceActivityEntity toEntity(DeviceActivity domain);
}