package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.model.ModelAppleSiliconDetailsService;
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
public class ModelAppleSiliconService implements ModelAppleSiliconDetailsService {

	private final ModelAppleSiliconRepository modelAppleSiliconRepository;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getAllAppleSiliconsForModel(Integer modelId) {
		return modelAppleSiliconRepository.getAppleSiliconsForModel(modelId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getAppleSilicon(Integer modelAppleSiliconId) {
		return NamedIdDto.from(modelAppleSiliconRepository.getAppleSilicon(modelAppleSiliconId));
	}
}