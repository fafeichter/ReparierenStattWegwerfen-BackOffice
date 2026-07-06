package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelAppleSiliconUnifiedMemory;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelAppleSiliconUnifiedMemoryRepository extends JpaRepository<ModelAppleSiliconUnifiedMemory,
	Integer> {

	@Query("""
		select m
		from ModelAppleSiliconUnifiedMemory m
		where m.id = :modelUnifiedMemoryId
		""")
	NamedId getUnifiedMemory(Integer modelUnifiedMemoryId);
}