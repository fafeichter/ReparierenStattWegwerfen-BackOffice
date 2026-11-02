package at.reparierenstattwegwerfen.backoffice.device.internal.domain.spareparts;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceInstalledSparePartRepository {

	DeviceInstalledSparePart getById(Integer id);

	DeviceInstalledSparePart save(DeviceInstalledSparePart sparePart);

	void deleteById(Integer id);

	List<DeviceInstalledSparePart> getSparePartsForDevice(Integer deviceId);
}