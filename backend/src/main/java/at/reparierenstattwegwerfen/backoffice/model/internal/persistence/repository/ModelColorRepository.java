package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelColorRepository extends JpaRepository<ModelColor, Integer> {

	@Query("""
		select m
		from ModelColor m
		where m.id = :modelColorId
		""")
	ModelColor getColor(Integer modelColorId);

	@Query("""
		select m.color
		from ModelAvailableColor m
		where m.model.id = :modelId
		""")
	List<ModelColor> getColorsForModel(Integer modelId);
}