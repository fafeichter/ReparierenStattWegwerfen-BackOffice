package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("""
            from Model m
            where m.modelSeries.id in (1, 2, 3)
            order by m.releaseYear asc, m.modelNumber asc
            """)
    List<Model> findAllMacbooks();

    @Query("""
            from Model m
            where m.modelSeries.id in (4, 5)
            order by m.releaseYear asc, m.modelNumber asc
            """)
    List<Model> findAllIPads();

    @Query("""
            select distinct m
            from Model m
            left join fetch m.modelSeries
            left join fetch m.availableColors ac
            left join fetch ac.color
            left join fetch m.availableFeatures af
            left join fetch af.feature f
            left join fetch f.modelFeatureCategory
            left join fetch m.availableAppleSilicons aas
            left join fetch aas.modelAppleSilicon
            left join fetch aas.availableStorages st
            left join fetch st.storage
            left join fetch aas.availableUnifiedMemories um
            left join fetch um.unifiedMemory
            where m.id = :modelId
            """)
    Model getModelDetails(Integer modelId);
}
