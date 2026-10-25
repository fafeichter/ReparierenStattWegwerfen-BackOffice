package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.model.ModelUnifiedMemoryDetailsService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 09.07.2026
 */
@Service
@RequiredArgsConstructor
public class ModelUnifiedMemoryService implements ModelUnifiedMemoryDetailsService {

	private final ModelAppleSiliconUnifiedMemoryRepository modelAppleSiliconUnifiedMemoryRepository;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return modelAppleSiliconUnifiedMemoryRepository.getUnifiedMemoriesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getUnifiedMemory(Integer modelUnifiedMemoryId) {
		return NamedIdDto.from(modelAppleSiliconUnifiedMemoryRepository.getUnifiedMemory(modelUnifiedMemoryId));
	}
}