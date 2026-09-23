package at.reparierenstattwegwerfen.backoffice.sparepart.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.sparepart.internal.persistence.model.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 23.09.2026
 */
@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {

	@Query("""
		SELECT s
		FROM SparePart s
		JOIN SparePartAvailableModelSeries spms
		  ON spms.sparePart.id = s.id
		WHERE spms.modelSeriesId = :modelSeriesId
		ORDER BY s.sortOrder
		""")
	List<SparePart> getSparePartsForModelSeries(Integer modelSeriesId);
}