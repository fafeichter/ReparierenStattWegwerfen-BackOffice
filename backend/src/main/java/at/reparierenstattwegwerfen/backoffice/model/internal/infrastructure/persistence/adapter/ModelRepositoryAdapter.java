package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.Model;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelRepository;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper.ModelPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository.SpringDataModelRepository;
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
public class ModelRepositoryAdapter implements ModelRepository {

	private final SpringDataModelRepository jpaRepository;
	private final ModelPersistenceMapper mapper;

	@Override
	public Optional<Model> findById(Integer id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public Model getModel(Integer modelId) {
		return mapper.toDomain(jpaRepository.getModel(modelId));
	}

	@Override
	public Model getModelDetails(Integer modelId) {
		return mapper.toDomainWithDetails(jpaRepository.getModelDetails(modelId));
	}

	@Override
	public List<Model> findAllMacbooks() {
		return jpaRepository.findAllMacbooks()
			.stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public List<Model> findAllIPads() {
		return jpaRepository.findAllIPads()
			.stream()
			.map(mapper::toDomain)
			.toList();
	}
}
