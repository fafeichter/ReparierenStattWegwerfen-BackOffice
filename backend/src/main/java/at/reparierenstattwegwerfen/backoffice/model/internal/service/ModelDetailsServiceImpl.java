package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelDetailsServiceImpl implements ModelDetailsService {

	private final ModelRepository modelRepository;
	private final ModelAppleSiliconRepository modelAppleSiliconRepository;
	private final ModelAppleSiliconUnifiedMemoryRepository modelAppleSiliconUnifiedMemoryRepository;
	private final ModelStorageRepository modelStorageRepository;
	private final ModelColorRepository modelColorRepository;

	@Override
	public NamedIdDto getModel(Integer modelId) {
		return NamedIdDto.from(modelRepository.getModel(modelId));
	}

	@Override
	public NamedIdDto getAppleSilicon(Integer modelAppleSiliconId) {
		return NamedIdDto.from(modelAppleSiliconRepository.getAppleSilicon(modelAppleSiliconId));

	}

	@Override
	public NamedIdDto getUnifiedMemory(Integer modelUnifiedMemoryId) {
		return NamedIdDto.from(modelAppleSiliconUnifiedMemoryRepository.getUnifiedMemory(modelUnifiedMemoryId));
	}

	@Override
	public NamedIdDto getStorage(Integer modelStorageId) {
		return NamedIdDto.from(modelStorageRepository.getStorage(modelStorageId));
	}

	@Override
	public NamedIdDto getColor(Integer modelColorId) {
		return NamedIdDto.from(modelColorRepository.getColor(modelColorId));
	}

	@Override
	public String getTechnicalSpecsUrl(Integer modelId) {
		return modelRepository.getModel(modelId).getTechnicalSpecsUrl();
	}

	@Override
	public String getModelNumber(Integer modelId) {
		return modelRepository.getModel(modelId).getModelNumber();
	}
}