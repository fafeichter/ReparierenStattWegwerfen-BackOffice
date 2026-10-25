package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAppleSiliconUnifiedMemory;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAppleSiliconUnifiedMemoryRepository;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper.ModelPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository.SpringDataModelAppleSiliconUnifiedMemoryRepository;
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
public class ModelAppleSiliconUnifiedMemoryRepositoryAdapter implements ModelAppleSiliconUnifiedMemoryRepository {

	private final SpringDataModelAppleSiliconUnifiedMemoryRepository jpaRepository;
	private final ModelPersistenceMapper mapper;

	@Override
	public Optional<ModelAppleSiliconUnifiedMemory> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public ModelAppleSiliconUnifiedMemory getUnifiedMemory(Integer modelUnifiedMemoryId) {
		return mapper.toDomain(jpaRepository.getUnifiedMemory(modelUnifiedMemoryId));
	}

	@Override
	public List<ModelAppleSiliconUnifiedMemory> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return jpaRepository.getUnifiedMemoriesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}
