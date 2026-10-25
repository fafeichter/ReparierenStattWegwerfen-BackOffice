package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelAppleSiliconUnifiedMemoryRepository {

	Optional<ModelAppleSiliconUnifiedMemory> findById(Integer id);

	ModelAppleSiliconUnifiedMemory getUnifiedMemory(Integer modelUnifiedMemoryId);

	List<ModelAppleSiliconUnifiedMemory> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}
