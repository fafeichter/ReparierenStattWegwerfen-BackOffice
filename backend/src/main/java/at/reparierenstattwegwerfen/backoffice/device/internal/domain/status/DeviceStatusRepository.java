package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceStatusRepository {

	DeviceStatus getById(Integer id);

	List<DeviceStatus> getAllNonSystemStatus();
}