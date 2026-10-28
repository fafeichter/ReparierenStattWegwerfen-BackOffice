package at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceNoteRepository {

	DeviceNote getById(Integer id);

	DeviceNote save(DeviceNote note);

	void deleteById(Integer id);

	List<DeviceNote> getNotesForDevice(Integer deviceId);
}