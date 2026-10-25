package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.ModelColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataModelColorRepository extends JpaRepository<ModelColorEntity, Integer> {

	@Query("""
		select m
		from ModelColorEntity m
		where m.id = :modelColorId
		""")
	ModelColorEntity getColor(Integer modelColorId);

	@Query("""
		select m.color
		from ModelAvailableColorEntity m
		where m.model.id = :modelId
		""")
	List<ModelColorEntity> getColorsForModel(Integer modelId);
}