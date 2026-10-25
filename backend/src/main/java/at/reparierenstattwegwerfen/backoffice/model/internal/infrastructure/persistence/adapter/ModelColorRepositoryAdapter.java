package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelColor;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelColorRepository;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper.ModelPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository.SpringDataModelColorRepository;
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
public class ModelColorRepositoryAdapter implements ModelColorRepository {

	private final SpringDataModelColorRepository jpaRepository;
	private final ModelPersistenceMapper mapper;

	@Override
	public Optional<ModelColor> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public ModelColor getColor(Integer modelColorId) {
		return mapper.toDomain(jpaRepository.getColor(modelColorId));
	}

	@Override
	public List<ModelColor> getColorsForModel(Integer modelId) {
		return jpaRepository.getColorsForModel(modelId)
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}
