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
		from ModelAppleSiliconUnifiedMemoryEntity memory
		where memory.id = :modelUnifiedMemoryId
		""")
	ModelAppleSiliconUnifiedMemoryEntity getUnifiedMemory(Integer modelUnifiedMemoryId);

	@Query("""
		select available_memory.unifiedMemory
		from ModelSiliconAvailableUnifiedMemoryEntity available_memory
		where available_memory.availableAppleSilicon.model.id = :modelId
		and available_memory.availableAppleSilicon.modelAppleSilicon.id= :appleSiliconId
		order by available_memory.unifiedMemory.sortOrder
		""")
	List<ModelAppleSiliconUnifiedMemoryEntity> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}
