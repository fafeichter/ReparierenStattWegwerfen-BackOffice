package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

public interface DeviceTagsRepository {

	void deleteByDeviceTagId(Integer deviceTagId);

	DeviceTags save(DeviceTags deviceTags);
}