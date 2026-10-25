package at.reparierenstattwegwerfen.backoffice.model.internal.domain;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelRepository {

	Optional<Model> findById(Integer id);

	Model getModel(Integer modelId);

	Model getModelDetails(Integer modelId);

	List<Model> findAllMacbooks();

	List<Model> findAllIPads();
}