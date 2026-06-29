package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelAppleSilicon;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelAppleSiliconRepository extends JpaRepository<ModelAppleSilicon, Integer> {

	@Query("""
		select m
		from ModelAppleSilicon m
		where m.id = :modelAppleSiliconId
		""")
    NamedId getAppleSilicon(Integer modelAppleSiliconId);
}