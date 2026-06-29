package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceBatteryStatus;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Integer> {
}