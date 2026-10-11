package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model.DeviceGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataDeviceGradeRepository extends JpaRepository<DeviceGradeEntity, Integer> {

	@Query("""
		SELECT n FROM DeviceGradeEntity n
		ORDER BY n.sortOrder
		""")
	List<DeviceGradeEntity> getAllGrades();
}