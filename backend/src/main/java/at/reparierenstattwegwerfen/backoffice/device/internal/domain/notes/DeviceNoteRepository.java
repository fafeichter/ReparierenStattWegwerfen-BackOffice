package at.reparierenstattwegwerfen.backoffice.device.internal.domain.notes;

import java.util.List;
import java.util.Optional;

public interface DeviceNoteRepository {

	Optional<DeviceNote> findById(Integer id);

	DeviceNote getReferenceById(Integer id);

	DeviceNote save(DeviceNote note);
	
	void deleteById(Integer id);

	List<DeviceNote> getNotesForDevice(Integer deviceId);
}