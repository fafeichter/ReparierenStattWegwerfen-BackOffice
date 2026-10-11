package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceStatusRepository {

	Optional<DeviceStatus> findById(Integer id);

	DeviceStatus getReferenceById(Integer id);

	List<DeviceStatus> getAllNonSystemStatus();
}