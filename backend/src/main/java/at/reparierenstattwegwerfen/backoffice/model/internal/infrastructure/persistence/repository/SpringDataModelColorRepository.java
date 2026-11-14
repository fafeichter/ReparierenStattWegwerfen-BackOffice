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
		select available_color.color
		from ModelAvailableColorEntity available_color
		where available_color.model.id = :modelId
		order by available_color.color.sortOrder
		""")
	List<ModelColorEntity> getColorsForModel(Integer modelId);
}