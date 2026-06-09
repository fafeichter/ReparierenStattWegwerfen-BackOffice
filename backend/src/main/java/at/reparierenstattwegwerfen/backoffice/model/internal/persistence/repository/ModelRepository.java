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
            SELECT DISTINCT m
            FROM Model m
            LEFT JOIN FETCH m.modelSeries
            LEFT JOIN FETCH m.availableColors ac
            LEFT JOIN FETCH ac.color
            LEFT JOIN FETCH m.availableFeatures af
            LEFT JOIN FETCH af.feature f
            LEFT JOIN FETCH f.modelFeatureCategory
            LEFT JOIN FETCH m.availableAppleSilicons aas
            LEFT JOIN FETCH aas.modelAppleSilicon
            LEFT JOIN FETCH aas.availableStorages st
            LEFT JOIN FETCH st.storage
            LEFT JOIN FETCH aas.availableUnifiedMemories um
            LEFT JOIN FETCH um.unifiedMemory
            WHERE m.id = :modelId
            """)
    Model getModelDetails(Integer modelId);
}
