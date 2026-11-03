package at.reparierenstattwegwerfen.backoffice.device.internal.domain.tags;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceAddedTagRepository {

	void removeTagFromDevice(Integer deviceTagId);

	void save(DeviceAddedTag deviceAddedTag);
}