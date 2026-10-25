package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.model.ModelColorDetailsService;
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
public class ModelColorService implements ModelColorDetailsService {

	private final ModelColorRepository modelColorRepository;

	@Transactional(readOnly = true)
	public List<NamedIdDto> getColorsForModel(Integer modelId) {
		return modelColorRepository.getColorsForModel(modelId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getColor(Integer modelColorId) {
		return NamedIdDto.from(modelColorRepository.getColor(modelColorId));
	}
}