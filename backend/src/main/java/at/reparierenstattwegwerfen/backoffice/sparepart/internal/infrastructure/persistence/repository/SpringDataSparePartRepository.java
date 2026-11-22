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
		from SparePartEntity s
		join SparePartAvailableModelSeriesEntity spms on spms.sparePart.id = s.id
		where spms.modelSeriesId = :modelSeriesId
		and s.id not in (:excludeIds)
		order by s.sortOrder
		""")
	List<SparePartEntity> getSparePartsForModelSeriesExcluding(Integer modelSeriesId, Set<Integer> excludeIds);
}