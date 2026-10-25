package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelStorage;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelStorageRepository;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper.ModelPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository.SpringDataModelStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Repository
@RequiredArgsConstructor
public class ModelStorageRepositoryAdapter implements ModelStorageRepository {

	private final SpringDataModelStorageRepository jpaRepository;
	private final ModelPersistenceMapper mapper;

	@Override
	public Optional<ModelStorage> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public ModelStorage getStorage(Integer modelStorageId) {
		return mapper.toDomain(jpaRepository.getStorage(modelStorageId));
	}

	@Override
	public List<ModelStorage> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return jpaRepository.getStoragesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}
