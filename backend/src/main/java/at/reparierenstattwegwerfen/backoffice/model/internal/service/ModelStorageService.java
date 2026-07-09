package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelStorageRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelStorageService {

	private final ModelStorageRepository modelStorageRepository;

	public List<NamedIdDto> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return modelStorageRepository.getStoragesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}
}