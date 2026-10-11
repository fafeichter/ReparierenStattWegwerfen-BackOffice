package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import java.util.List;
import java.util.Optional;

public interface DeviceGradeRepository {

	Optional<DeviceGrade> findById(Integer id);

	DeviceGrade getReferenceById(Integer id);

	List<DeviceGrade> getAllGrades();
}