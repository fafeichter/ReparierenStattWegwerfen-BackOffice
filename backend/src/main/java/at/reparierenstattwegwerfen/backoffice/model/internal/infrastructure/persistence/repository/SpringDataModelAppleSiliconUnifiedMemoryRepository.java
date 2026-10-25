package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.ModelAppleSiliconUnifiedMemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataModelAppleSiliconUnifiedMemoryRepository extends JpaRepository<ModelAppleSiliconUnifiedMemoryEntity, Integer> {

	@Query("""
		select m
		from ModelAppleSiliconUnifiedMemoryEntity m
		where m.id = :modelUnifiedMemoryId
		""")
	ModelAppleSiliconUnifiedMemoryEntity getUnifiedMemory(Integer modelUnifiedMemoryId);

	@Query("""
		select m.unifiedMemory
		from ModelSiliconAvailableUnifiedMemoryEntity m
		where m.availableAppleSilicon.model.id = :modelId
		and m.availableAppleSilicon.modelAppleSilicon.id= :appleSiliconId
		""")
	List<ModelAppleSiliconUnifiedMemoryEntity> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}
