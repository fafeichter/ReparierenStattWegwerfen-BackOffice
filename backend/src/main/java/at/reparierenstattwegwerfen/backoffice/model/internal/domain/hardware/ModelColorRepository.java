package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelColorRepository {

	Optional<ModelColor> findById(Integer id);

	ModelColor getColor(Integer modelColorId);

	List<ModelColor> getColorsForModel(Integer modelId);
}
