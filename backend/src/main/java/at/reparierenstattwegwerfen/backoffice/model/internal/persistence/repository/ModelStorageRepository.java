package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelStorageRepository extends JpaRepository<ModelStorage, Integer> {

	@Query("""
		select m
		from ModelStorage m
		where m.id = :modelStorageId
		""")
	ModelStorage getStorage(Integer modelStorageId);

	@Query("""
		select m.storage
		from ModelSiliconAvailableStorage m
		where m.availableAppleSilicon.model.id = :modelId
				and m.availableAppleSilicon.modelAppleSilicon.id= :appleSiliconId
		""")
	List<ModelStorage> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId);
}