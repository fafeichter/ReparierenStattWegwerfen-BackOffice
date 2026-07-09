package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelAppleSiliconUnifiedMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelUnifiedMemoryRepository extends JpaRepository<ModelAppleSiliconUnifiedMemory, Integer> {

	@Query("""
		select m.unifiedMemory
		from ModelSiliconAvailableUnifiedMemory m
		where m.availableAppleSilicon.model.id = :modelId
		and m.availableAppleSilicon.modelAppleSilicon.id= :appleSiliconId
		""")
	List<ModelAppleSiliconUnifiedMemory> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}