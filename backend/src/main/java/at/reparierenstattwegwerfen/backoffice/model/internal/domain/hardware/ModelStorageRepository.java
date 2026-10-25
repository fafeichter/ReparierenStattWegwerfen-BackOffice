package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelStorageRepository {

	Optional<ModelStorage> findById(Integer id);

	ModelStorage getStorage(Integer modelStorageId);

	List<ModelStorage> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}
