package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelAppleSilicon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	ModelAppleSilicon getAppleSilicon(Integer modelAppleSiliconId);

	@Query("""
		select m.modelAppleSilicon
		from ModelAvailableAppleSilicon m
		where m.model.id = :modelId
		""")
	List<ModelAppleSilicon> getAppleSiliconsForModel(Integer modelId);
}