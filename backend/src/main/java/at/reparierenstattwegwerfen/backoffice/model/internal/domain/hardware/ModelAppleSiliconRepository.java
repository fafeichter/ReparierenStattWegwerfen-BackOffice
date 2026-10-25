package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelAppleSiliconRepository {

	Optional<ModelAppleSilicon> findById(Integer id);

	ModelAppleSilicon getAppleSilicon(Integer modelAppleSiliconId);

	List<ModelAppleSilicon> getAppleSiliconsForModel(Integer modelId);
}
