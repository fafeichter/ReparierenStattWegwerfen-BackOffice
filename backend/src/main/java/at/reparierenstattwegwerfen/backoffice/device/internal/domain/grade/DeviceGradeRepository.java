package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceGradeRepository {

	Optional<DeviceGrade> findById(Integer id);

	DeviceGrade getReferenceById(Integer id);

	List<DeviceGrade> getAllGrades();
}