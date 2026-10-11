package at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts;

import java.util.List;
import java.util.Optional;

public interface DeviceSparePartsRepository {

	Optional<DeviceSpareParts> findById(Integer id);

	DeviceSpareParts getReferenceById(Integer id);

	DeviceSpareParts save(DeviceSpareParts sparePart);

	void deleteById(Integer id);

	List<DeviceSpareParts> getSparePartsForDevice(Integer deviceId);
}