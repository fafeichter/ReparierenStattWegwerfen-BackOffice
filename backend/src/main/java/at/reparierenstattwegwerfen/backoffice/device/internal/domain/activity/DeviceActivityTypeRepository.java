package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceActivityTypeRepository {

	Optional<DeviceActivityType> findById(Integer id);

	DeviceActivityType getById(Integer id);
}