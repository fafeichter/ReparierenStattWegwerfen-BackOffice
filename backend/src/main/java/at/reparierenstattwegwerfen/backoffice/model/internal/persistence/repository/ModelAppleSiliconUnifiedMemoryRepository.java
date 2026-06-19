package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelAppleSiliconUnifiedMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelAppleSiliconUnifiedMemoryRepository extends JpaRepository<ModelAppleSiliconUnifiedMemory,
        Integer> {
}
