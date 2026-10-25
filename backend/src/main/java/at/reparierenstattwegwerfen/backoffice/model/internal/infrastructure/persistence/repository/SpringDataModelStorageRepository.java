package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.ModelStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
@Repository
public interface SpringDataModelStorageRepository extends JpaRepository<ModelStorageEntity, Integer> {

	@Query("""
		select m
		from ModelStorageEntity m
		where m.id = :modelStorageId
		""")
	ModelStorageEntity getStorage(Integer modelStorageId);

	@Query("""
		select m.storage
		from ModelSiliconAvailableStorageEntity m
		where m.availableAppleSilicon.model.id = :modelId
				and m.availableAppleSilicon.modelAppleSilicon.id= :appleSiliconId
		""")
	List<ModelStorageEntity> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}