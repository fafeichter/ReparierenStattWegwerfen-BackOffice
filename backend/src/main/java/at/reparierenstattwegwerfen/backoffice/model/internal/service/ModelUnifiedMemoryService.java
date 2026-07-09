package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelUnifiedMemoryRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelUnifiedMemoryService {

	private final ModelUnifiedMemoryRepository modelUnifiedMemoryRepository;

	public List<NamedIdDto> getUnifiedMemoriesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return modelUnifiedMemoryRepository.getUnifiedMemoriesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}
}