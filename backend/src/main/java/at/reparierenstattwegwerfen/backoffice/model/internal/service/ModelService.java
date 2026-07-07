package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.*;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@Slf4j
@RequiredArgsConstructor
@RegisterReflectionForBinding({
	PromptContext.class,
	ModelMatchResponse.class,
	ModelMatchResponse.AlternativeCandidate.class
})
public class ModelService {

	private final ModelRepository modelRepository;
	private final ModelMatchExtractor modelMatchExtractor;
	private final ModelResolutionService modelResolutionService;

	public List<ModelDto> getAllMacBooks() {
		return toModelDtos(modelRepository.findAllMacbooks());
	}

	public List<ModelDto> getAllIPads() {
		return toModelDtos(modelRepository.findAllIPads());
	}

	private List<ModelDto> toModelDtos(List<Model> models) {
		return models.stream()
			.map(model -> ModelDto.builder()
				.id(model.getId())
				.name(model.getName())
				.modelNumber(model.getModelNumber())
				.releaseYear(model.getReleaseYear())
				.releaseMonth(model.getReleaseMonth())
				.build())
			.toList();
	}

	public ModelDetailDto getModelDetails(Integer modelId) {
		Model model = modelRepository.getModelDetails(modelId);

		return ModelDetailDto.builder()
			.id(model.getId())
			.name(model.getName())
			.series(model.getModelSeries().getName())
			.modelNumber(model.getModelNumber())
			.technicalSpecsUrl(model.getTechnicalSpecsUrl())
			.releaseYear(model.getReleaseYear())
			.displaySize(model.getDisplaySize())
			.displaySizeExact(model.getDisplaySizeExact())
			.colors(model.getAvailableColors().stream()
				.map(mac -> ModelDetailDto.ColorDto.builder()
					.id(mac.getColor().getId())
					.name(mac.getColor().getName())
					.build())
				.toList())
			.features(model.getAvailableFeatures().stream()
				.map(this::toFeatureDto)
				.toList())
			.siliconOptions(model.getAvailableAppleSilicons().stream()
				.map(this::toSiliconDto)
				.toList())
			.build();
	}

	private ModelDetailDto.FeatureDto toFeatureDto(ModelAvailableFeature maf) {
		ModelFeature feature = maf.getFeature();
		return ModelDetailDto.FeatureDto.builder()
			.id(feature.getId())
			.name(feature.getName())
			.category(feature.getModelFeatureCategory().getValue())
			.build();
	}

	private ModelDetailDto.SiliconDto toSiliconDto(ModelAvailableAppleSilicon aas) {
		ModelAppleSilicon chip = aas.getModelAppleSilicon();
		return ModelDetailDto.SiliconDto.builder()
			.id(chip.getId())
			.name(chip.getName())
			.nameShort(chip.getNameShort())
			.numberCpuEfficiencyCores(chip.getNumberCpuEfficiencyCores())
			.numberCpuPerformanceCores(chip.getNumberCpuPerformanceCores())
			.numberCpuSuperCores(chip.getNumberCpuSuperCores())
			.numberGpuCores(chip.getNumberGpuCores())
			.storageOptions(aas.getAvailableStorages().stream()
				.map(s -> toSizeDto(s.getStorage().getId(),
					s.getStorage().getSize(), s.getStorage().getUnit()))
				.toList())
			.memoryOptions(aas.getAvailableUnifiedMemories().stream()
				.map(m -> toSizeDto(m.getUnifiedMemory().getId(),
					m.getUnifiedMemory().getSize(), m.getUnifiedMemory().getUnit()))
				.toList())
			.build();
	}

	private ModelDetailDto.SizeDto toSizeDto(Integer id, Short size, DataUnit unit) {
		return ModelDetailDto.SizeDto.builder()
			.id(id)
			.size(size)
			.unit(DataUnitConverter.toSuffix(unit))
			.build();
	}

	public ResolvedModelMatch getModelDetailsFromAd(String adUrl) {
		ModelMatchResponse rawAiResponse = modelMatchExtractor.extractModelDetails(adUrl);
		return modelResolutionService.resolveToDomainResponse(rawAiResponse);
	}
}
