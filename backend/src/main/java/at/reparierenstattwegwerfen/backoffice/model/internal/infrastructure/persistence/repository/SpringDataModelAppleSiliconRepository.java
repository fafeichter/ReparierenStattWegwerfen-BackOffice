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
		from ModelAppleSiliconEntity silicon
		where silicon.id = :modelAppleSiliconId
		""")
	ModelAppleSiliconEntity getAppleSilicon(Integer modelAppleSiliconId);

	@Query("""
		select available_silicon.modelAppleSilicon
		from ModelAvailableAppleSiliconEntity available_silicon
		where available_silicon.model.id = :modelId
		order by available_silicon.modelAppleSilicon.sortOrder
		""")
	List<ModelAppleSiliconEntity> getAppleSiliconsForModel(Integer modelId);
}