package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.Device;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTag;
import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface DeviceTagsRepository extends JpaRepository<DeviceTags, Integer> {

	void deleteByDeviceAndDeviceTag(Device device, DeviceTag deviceTag);
}