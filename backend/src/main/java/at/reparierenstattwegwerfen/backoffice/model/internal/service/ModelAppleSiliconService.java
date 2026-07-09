package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.ModelAppleSiliconRepository;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelAppleSiliconService {

	private final ModelAppleSiliconRepository modelAppleSiliconRepository;

	public List<NamedIdDto> getAllAppleSiliconsForModel(Integer modelId) {
		return modelAppleSiliconRepository.getAppleSiliconsForModel(modelId).stream()
			.map(NamedIdDto::from)
			.toList();
	}
}