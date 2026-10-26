package at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.model.SparePartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Repository
public interface SpringDataSparePartRepository extends JpaRepository<SparePartEntity, Integer> {

	@Query("""
		SELECT s
		FROM SparePartEntity s
		JOIN SparePartAvailableModelSeriesEntity spms
		  ON spms.sparePart.id = s.id
		WHERE spms.modelSeriesId = :modelSeriesId
		  AND s.id NOT IN (:excludeIds)
		ORDER BY s.sortOrder
		""")
	List<SparePartEntity> getSparePartsForModelSeriesExcluding(Integer modelSeriesId, Set<Integer> excludeIds);
}