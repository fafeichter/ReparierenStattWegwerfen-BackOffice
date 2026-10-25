package at.reparierenstattwegwerfen.backoffice.model.internal.domain;

import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.DataUnits;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAppleSilicon;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware.ModelAvailableAppleSilicon;
import at.reparierenstattwegwerfen.backoffice.model.internal.interfaces.dtos.ModelDetailDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.interfaces.dtos.ModelDto;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import java.time.YearMonth;
import java.util.List;

/**
 * @author Fabian Feichter
 * @since 09.06.2026
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ModelService implements ModelDetailsService {

	private final ModelRepository modelRepository;

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getModel(Integer modelId) {
		return NamedIdDto.from(modelRepository.getModel(modelId));
	}

	@Transactional(readOnly = true)
	@Override
	public String getTechnicalSpecsUrl(Integer modelId) {
		return modelRepository.getModel(modelId).getTechnicalSpecsUrl();
	}

	@Transactional(readOnly = true)
	@Override
	public String getModelNumber(Integer modelId) {
		return modelRepository.getModel(modelId).getModelNumber();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getModelSeries(Integer modelId) {
		return NamedIdDto.from(modelRepository.getModel(modelId).getModelSeries());
	}

	@Transactional(readOnly = true)
	public List<ModelDto> getAllMacBooks() {
		return toModelDtos(modelRepository.findAllMacbooks());
	}

	@Transactional(readOnly = true)
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

	@Transactional(readOnly = true)
	public ModelDetailDto getModelDetails(Integer modelId) {
		Model model = modelRepository.getModelDetails(modelId);

		return ModelDetailDto.builder()
			.id(model.getId())
			.name(model.getName())
			.series(model.getModelSeries().getName())
			.modelNumber(model.getModelNumber())
			.technicalSpecsUrl(model.getTechnicalSpecsUrl())
			.releaseDate(YearMonth.of(model.getReleaseYear(), model.getReleaseMonth()))
			.displaySize(model.getDisplaySize())
			.displaySizeExact(model.getDisplaySizeExact())
			.colors(model.getAvailableColors()
				.stream()
				.map(color -> ModelDetailDto.ColorDto.builder()
					.id(color.getId())
					.name(color.getName())
					.build())
				.toList())
			.features(model.getAvailableFeatures()
				.stream()
				.map(this::toFeatureDto)
				.toList())
			.siliconOptions(model.getAvailableAppleSilicons()
				.stream()
				.map(this::toSiliconDto)
				.toList())
			.build();
	}

	private ModelDetailDto.FeatureDto toFeatureDto(ModelFeature feature) {
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
			.storageOptions(aas.getAvailableStorages()
				.stream()
				.map(s -> toSizeDto(s.getId(), s.getSize(), s.getUnit()))
				.toList())
			.memoryOptions(aas.getAvailableUnifiedMemories()
				.stream()
				.map(m -> toSizeDto(m.getId(), m.getSize(), m.getUnit()))
				.toList())
			.build();
	}

	private ModelDetailDto.SizeDto toSizeDto(Integer id, Short size, DataUnit unit) {
		return ModelDetailDto.SizeDto.builder()
			.id(id)
			.size(size)
			.unit(DataUnits.toSuffix(unit))
			.build();
	}
}