package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelColor;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    NamedId getColor(Integer modelColorId);
}