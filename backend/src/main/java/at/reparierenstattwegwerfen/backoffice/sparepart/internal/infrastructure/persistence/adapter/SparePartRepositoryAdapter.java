package at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain.SparePart;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain.SparePartRepository;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.mapper.SparePartPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.repository.SpringDataSparePartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Repository
@RequiredArgsConstructor
public class SparePartRepositoryAdapter implements SparePartRepository {

	private final SpringDataSparePartRepository jpaRepository;
	private final SparePartPersistenceMapper mapper;

	@Override
	public SparePart getSparePartById(Integer sparePartId) {
		return mapper.toDomain(jpaRepository.getReferenceById(sparePartId));
	}

	@Override
	public List<SparePart> getSparePartsForModelSeriesExcluding(Integer modelSeriesId, Set<Integer> excludeIds) {
		return jpaRepository.getSparePartsForModelSeriesExcluding(modelSeriesId, excludeIds)
			.stream()
			.map(mapper::toDomain).toList();
	}
}