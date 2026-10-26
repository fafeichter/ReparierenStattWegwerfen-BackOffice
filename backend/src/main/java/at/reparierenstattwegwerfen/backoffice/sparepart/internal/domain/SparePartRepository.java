package at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain;

import java.util.List;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
public interface SparePartRepository {

	SparePart getSparePartById(Integer sparePartId);

	List<SparePart> getSparePartsForModelSeriesExcluding(Integer modelSeriesId, Set<Integer> excludeIds);
}