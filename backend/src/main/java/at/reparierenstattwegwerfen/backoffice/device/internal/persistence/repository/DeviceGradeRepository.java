package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model.DeviceGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface DeviceGradeRepository extends JpaRepository<DeviceGrade, Integer> {

	@Query("""
		SELECT n FROM DeviceGrade n
		ORDER BY n.sortOrder ASC
		""")
	List<DeviceGrade> getAllStatus();
}