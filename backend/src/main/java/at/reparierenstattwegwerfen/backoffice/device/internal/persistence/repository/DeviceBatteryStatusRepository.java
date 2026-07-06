package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceBatteryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceBatteryStatusRepository extends JpaRepository<DeviceBatteryStatus, Integer> {
}