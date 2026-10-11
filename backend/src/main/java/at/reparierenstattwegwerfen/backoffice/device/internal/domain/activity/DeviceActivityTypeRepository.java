package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import java.util.Optional;

public interface DeviceActivityTypeRepository {

	Optional<DeviceActivityType> findById(Integer id);

	DeviceActivityType getReferenceById(Integer id);
}