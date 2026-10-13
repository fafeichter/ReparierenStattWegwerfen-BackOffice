package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceTagsRepository {

	void deleteByDeviceTagId(Integer deviceTagId);

	void save(DeviceTags deviceTags);
}