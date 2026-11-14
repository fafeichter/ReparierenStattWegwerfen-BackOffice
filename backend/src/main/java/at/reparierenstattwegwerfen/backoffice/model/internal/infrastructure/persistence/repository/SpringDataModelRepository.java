package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 09.06.2026
 */
@Repository
public interface SpringDataModelRepository extends JpaRepository<ModelEntity, Integer> {

	@Query("""
		from ModelEntity m
		where m.modelSeries.id in (1, 2, 3)
		order by m.releaseYear asc, m.releaseMonth asc, m.displaySizeExact asc, m.modelSeries.sortOrder
		""")
	List<ModelEntity> findAllMacBooks();

	@Query("""
		from ModelEntity m
		where m.modelSeries.id in (4, 5)
		order by m.releaseYear asc, m.releaseMonth asc, m.displaySizeExact asc, m.modelSeries.sortOrder
		""")
	List<ModelEntity> findAllIPads();

	@Query("""
		select m
		from ModelEntity m
		left join fetch m.modelSeries
		left join fetch m.availableColors ac
		left join fetch m.availableFeatures af
		left join fetch m.availableAppleSilicons aas
		left join fetch aas.modelAppleSilicon
		left join fetch aas.availableStorages st
		left join fetch aas.availableUnifiedMemories um
		where m.id = :modelId
		""")
	ModelEntity getModelDetails(Integer modelId);

	@Query("""
		select m
		from ModelEntity m
		where m.id = :modelId
		""")
	ModelEntity getModel(Integer modelId);
}