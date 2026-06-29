package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.ModelStorage;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    NamedId getStorage(Integer modelStorageId);
}