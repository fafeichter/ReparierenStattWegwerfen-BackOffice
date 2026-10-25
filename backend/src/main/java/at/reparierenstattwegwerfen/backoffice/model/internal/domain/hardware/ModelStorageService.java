package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.model.ModelStorageDetailsService;
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
public class ModelStorageService implements ModelStorageDetailsService {

	private final ModelStorageRepository modelStorageRepository;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getStoragesForModelAndAppleSilicon(Integer modelId, Integer appleSiliconId) {
		return modelStorageRepository.getStoragesForModelAndAppleSilicon(modelId, appleSiliconId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getStorage(Integer modelStorageId) {
		return NamedIdDto.from(modelStorageRepository.getStorage(modelStorageId));
	}
}