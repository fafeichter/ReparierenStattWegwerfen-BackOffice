package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.controller.ModelDetailDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.controller.ModelDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model.*;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelRepository;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.unit.DataUnit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
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

    public List<String> getModelNumberFromAdUrl(String adUrl) throws IOException {

        // 3. Build the context and render the Mustache template
        PromptContext context = PromptContext.builder()
                .adUrl(adUrl)
                .build();

        String templateString = mustacheTemplateResource.getContentAsString(StandardCharsets.UTF_8);
        Template template = Mustache.compiler().compile(templateString);
        String formattedPrompt = template.execute(context);

        // 4. Call Spring AI ChatClient with the rendered prompt
        ChatClient chatClient = aiClientBuilder.build();

        // We use .entity(LLMResponse.class) so Spring AI automatically parses
        // the strict raw JSON string back into your structured Lombok DTO.

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LLMResponse airResponse = chatClient.prompt(formattedPrompt)
                .call()
                .entity(LLMResponse.class);
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());


        // 5. Extract the highest probability model number if available
        if (airResponse != null && airResponse.getModelNumbers() != null && !airResponse.getModelNumbers().isEmpty()) {
            return airResponse.getModelNumbers();
        }

        return List.of();
    }
}
