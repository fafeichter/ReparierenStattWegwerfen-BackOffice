package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.controller.ModelDetailDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.controller.ModelDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.*;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@Slf4j
@RequiredArgsConstructor
@RegisterReflectionForBinding({PromptContext.class, DeviceModelMatchResponse.class,
	DeviceModelMatchResponse.AlternativeCandidate.class, DeviceModelMatchResponse.Confidence.class})
public class ModelService {

	private final ModelRepository modelRepository;
	private final ModelAppleSiliconRepository modelAppleSiliconRepository;
	private final ModelColorRepository modelColorRepository;
	private final ModelStorageRepository modelStorageRepository;
	private final ModelAppleSiliconUnifiedMemoryRepository modelAppleSiliconUnifiedMemoryRepository;

	private final ChatClient.Builder aiClientBuilder;

	@Value("classpath:prompts/model/apple-model-extractor.mustache")
	private Resource mustacheTemplateResource;

	public List<ModelDto> getAllMacBooks() {
		return modelRepository.findAllMacbooks()
			.stream()
			.map(macbook -> ModelDto.builder()
				.id(macbook.getId())
				.name(macbook.getName())
				.modelNumber(macbook.getModelNumber())
				.releaseYear(macbook.getReleaseYear())
				.releaseMonth(macbook.getReleaseMonth())
				.build())
			.toList();
	}

	public List<ModelDto> getAllIPads() {
		return modelRepository.findAllIPads()
			.stream()
			.map(macbook -> ModelDto.builder()
				.id(macbook.getId())
				.name(macbook.getName())
				.modelNumber(macbook.getModelNumber())
				.releaseYear(macbook.getReleaseYear())
				.releaseMonth(macbook.getReleaseMonth())
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

	@Transactional
	public AiResponse getModelDetailsFromAd(String adUrl) throws IOException {

		// 3. Build the context and render the Mustache template
		PromptContext context = PromptContext.builder()
			.adHtmlContent(HtmlMinifier.stripHtmlTrash(RestClient.create().get().uri(adUrl).retrieve().body(String.class)))
			.build();

		String templateString = mustacheTemplateResource.getContentAsString(StandardCharsets.UTF_8);
		Template template = Mustache.compiler().compile(templateString);
		String formattedPrompt = template.execute(context);

		ChatClient chatClient = aiClientBuilder.build();

		BeanOutputConverter<DeviceModelMatchResponse> output = new BeanOutputConverter<>(DeviceModelMatchResponse.class);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
			.outputSchema(output.getJsonSchema())
			.build();

		Prompt prompt = new Prompt(formattedPrompt, chatOptions);

		DeviceModelMatchResponse aiResponse = chatClient.prompt(prompt)
			.call()
			.entity(DeviceModelMatchResponse.class);
		stopWatch.stop();

		log.info(stopWatch.prettyPrint());

		AiResponse processedResponse = null;

		if (aiResponse != null) {
			// 1. Resolve the main NamedId components from your repositories
			NamedId model = modelRepository.findById(aiResponse.modelId()).orElse(null);
			NamedId color = aiResponse.modelColorId().flatMap(modelColorRepository::findById).orElse(null);
			NamedId storage = aiResponse.modelStorageId().flatMap(modelStorageRepository::findById).orElse(null);
			NamedId memory = aiResponse.modelAppleSiliconUnifiedMemoryId().flatMap(modelAppleSiliconUnifiedMemoryRepository::findById).orElse(null);


			// 2. Map the alternative candidates to the new record structure recursively
			List<AiResponse.Alternative> mappedAlternatives = null;
			if (aiResponse.alternativeCandidates() != null) {
				mappedAlternatives = aiResponse.alternativeCandidates().stream()
					.map(alt -> new AiResponse.Alternative(
						NamedIdDto.from(modelRepository.findById(alt.modelId()).orElse(null)),
						NamedIdDto.from(alt.modelColorId().flatMap(modelColorRepository::findById).orElse(null)),
						NamedIdDto.from(alt.modelStorageId().flatMap(modelStorageRepository::findById).orElse(null)),
						NamedIdDto.from(alt.modelAppleSiliconUnifiedMemoryId().flatMap(modelAppleSiliconUnifiedMemoryRepository::findById).orElse(null))
					))
					.toList();
			}

			// 3. Create the final top-level record
			processedResponse = new AiResponse(
				NamedIdDto.from(model),
				NamedIdDto.from(color),
				NamedIdDto.from(storage),
				NamedIdDto.from(memory),
				aiResponse.batteryMaximumCapacity().orElse(null),
				aiResponse.batteryCycleCount().orElse(null),
				aiResponse.reportedDefect().orElse(null),
				aiResponse.serialNumber().orElse(null),
				mappedAlternatives,
				stopWatch.getTotalTimeSeconds(),
				aiResponse.confidence().name()
			);
		}

		return processedResponse;
	}
}