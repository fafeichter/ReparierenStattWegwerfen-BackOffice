package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.Model;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelFeature;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelFeatureCategory;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelSeries;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.*;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Mapper
public interface ModelPersistenceMapper {

	@Mapping(target = "availableColors", ignore = true)
	@Mapping(target = "availableFeatures", ignore = true)
	@Mapping(target = "availableAppleSilicons", ignore = true)
	Model toDomain(ModelEntity entity);

	/**
	 * Also maps colors, features and chips with their options, each in {@code sort_order}.
	 */
	@Named("withDetails")
	Model toDomainWithDetails(ModelEntity entity);

	ModelAvailableAppleSilicon toDomain(ModelAvailableAppleSiliconEntity entity);

	ModelSeries toDomain(ModelSeriesEntity entity);

	ModelFeature toDomain(ModelFeatureEntity entity);

	ModelFeatureCategory toDomain(ModelFeatureCategoryEntity entity);

	ModelColor toDomain(ModelColorEntity entity);

	ModelAppleSilicon toDomain(ModelAppleSiliconEntity entity);

	ModelStorage toDomain(ModelStorageEntity entity);

	ModelAppleSiliconUnifiedMemory toDomain(ModelAppleSiliconUnifiedMemoryEntity entity);

	/**
	 * Chips sorted by the chip's {@code sort_order}; {@code @OrderBy} on the entity cannot reach it.
	 */
	default List<ModelAvailableAppleSilicon> toDomain(Set<ModelAvailableAppleSiliconEntity> entities) {
		return entities.stream()
			.sorted(Comparator.comparing(e -> e.getModelAppleSilicon().getSortOrder()))
			.map(this::toDomain)
			.toList();
	}
}