package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceNoteRepository extends JpaRepository<DeviceNote, Integer> {

	@Query("""
		SELECT n FROM DeviceNote n
		WHERE n.deviceId = :deviceId
		ORDER BY n.date DESC
		""")
	List<DeviceNote> getNotesForDevice(Integer deviceId);
}