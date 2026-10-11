package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

import java.util.List;
import java.util.Optional;

public interface DeviceTagRepository {

	Optional<DeviceTag> findById(Integer id);

	DeviceTag getReferenceById(Integer id);

	List<DeviceTag> getTagsForDevice(Integer deviceId);

	List<DeviceTag> getAvailableTagsForDevice(Integer deviceId, Integer modelSeriesId);
}