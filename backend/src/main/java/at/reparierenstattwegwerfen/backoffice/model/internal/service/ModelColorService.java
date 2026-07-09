package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelColorRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelColorService {

	private final ModelColorRepository modelColorRepository;

	public List<NamedIdDto> getColorsForModel(Integer modelId) {
		return modelColorRepository.getColorsForModel(modelId)
			.stream()
			.map(NamedIdDto::from)
			.toList();
	}
}