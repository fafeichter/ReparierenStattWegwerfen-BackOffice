package at.reparierenstattwegwerfen.backoffice.device.internal.domain.grade;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 12.10.2026
 */
public interface DeviceGradeRepository {

	DeviceGrade getById(Integer id);

	List<DeviceGrade> getAllGrades();
}