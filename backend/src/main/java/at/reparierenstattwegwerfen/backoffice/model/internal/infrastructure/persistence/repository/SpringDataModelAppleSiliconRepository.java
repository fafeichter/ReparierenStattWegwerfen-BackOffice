package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.ModelAppleSiliconEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataModelAppleSiliconRepository extends JpaRepository<ModelAppleSiliconEntity, Integer> {

	@Query("""
		select m
		from ModelAppleSiliconEntity m
		where m.id = :modelAppleSiliconId
		""")
	ModelAppleSiliconEntity getAppleSilicon(Integer modelAppleSiliconId);

	@Query("""
		select m.modelAppleSilicon
		from ModelAvailableAppleSiliconEntity m
		where m.model.id = :modelId
		""")
	List<ModelAppleSiliconEntity> getAppleSiliconsForModel(Integer modelId);
}