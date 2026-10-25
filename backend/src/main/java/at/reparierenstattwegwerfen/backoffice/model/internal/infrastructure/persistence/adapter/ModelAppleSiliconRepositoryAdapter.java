package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAppleSilicon;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAppleSiliconRepository;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper.ModelPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository.SpringDataModelAppleSiliconRepository;
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
public class ModelAppleSiliconRepositoryAdapter implements ModelAppleSiliconRepository {

	private final SpringDataModelAppleSiliconRepository jpaRepository;
	private final ModelPersistenceMapper mapper;

	@Override
	public Optional<ModelAppleSilicon> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public ModelAppleSilicon getAppleSilicon(Integer modelAppleSiliconId) {
		return mapper.toDomain(jpaRepository.getAppleSilicon(modelAppleSiliconId));
	}

	@Override
	public List<ModelAppleSilicon> getAppleSiliconsForModel(Integer modelId) {
		return jpaRepository.getAppleSiliconsForModel(modelId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}
