package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.mapper;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.Model;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelFeature;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelFeatureCategory;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelSeries;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.*;
import at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Mapper(componentModel = "spring")
public interface ModelPersistenceMapper {

	@Mapping(target = "availableColors", ignore = true)
	@Mapping(target = "availableFeatures", ignore = true)
	@Mapping(target = "availableAppleSilicons", ignore = true)
	Model toDomain(ModelEntity entity);

	ModelSeries toDomain(ModelSeriesEntity entity);

	ModelFeature toDomain(ModelFeatureEntity entity);

	ModelFeatureCategory toDomain(ModelFeatureCategoryEntity entity);

	ModelColor toDomain(ModelColorEntity entity);

	ModelAppleSilicon toDomain(ModelAppleSiliconEntity entity);

	ModelStorage toDomain(ModelStorageEntity entity);

	ModelAppleSiliconUnifiedMemory toDomain(ModelAppleSiliconUnifiedMemoryEntity entity);

	@Mapping(target = "availableStorages", ignore = true)
	@Mapping(target = "availableUnifiedMemories", ignore = true)
	ModelAvailableAppleSilicon toDomain(ModelAvailableAppleSiliconEntity entity);

	default Model toDomainWithDetails(ModelEntity entity) {
		if (entity == null) {
			return null;
		}

		Model model = toDomain(entity);
		model.setAvailableColors(entity.getAvailableColors()
			.stream()
			.map(ModelAvailableColorEntity::getColor)
			.map(this::toDomain)
			.toList());
		model.setAvailableFeatures(entity.getAvailableFeatures()
			.stream()
			.map(ModelAvailableFeatureEntity::getFeature)
			.map(this::toDomain)
			.toList());
		model.setAvailableAppleSilicons(entity.getAvailableAppleSilicons()
			.stream()
			.map(this::toDomainWithOptions)
			.toList());
		return model;
	}

	default ModelAvailableAppleSilicon toDomainWithOptions(ModelAvailableAppleSiliconEntity entity) {
		ModelAvailableAppleSilicon availableAppleSilicon = toDomain(entity);
		availableAppleSilicon.setAvailableStorages(entity.getAvailableStorages()
			.stream()
			.map(s -> toDomain(s.getStorage()))
			.toList());
		availableAppleSilicon.setAvailableUnifiedMemories(entity.getAvailableUnifiedMemories()
			.stream()
			.map(m -> toDomain(m.getUnifiedMemory()))
			.toList());
		return availableAppleSilicon;
	}
}