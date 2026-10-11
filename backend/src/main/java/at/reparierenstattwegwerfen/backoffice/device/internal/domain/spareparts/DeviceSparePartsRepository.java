package at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceSparePartsRepository {

	Optional<DeviceSpareParts> findById(Integer id);

	DeviceSpareParts getReferenceById(Integer id);

	DeviceSpareParts save(DeviceSpareParts sparePart);

	void deleteById(Integer id);

	List<DeviceSpareParts> getSparePartsForDevice(Integer deviceId);
}