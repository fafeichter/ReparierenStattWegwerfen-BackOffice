package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceTagRepository {

	DeviceTag getById(Integer id);

	List<DeviceTag> getTagsForDevice(Integer deviceId);

	List<DeviceTag> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId);
}