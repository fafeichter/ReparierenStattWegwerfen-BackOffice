package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.ModelDetailsService;
import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Service
@RequiredArgsConstructor
public class ModelDetailsServiceImpl implements ModelDetailsService {

	private final ModelRepository modelRepository;
	private final ModelAppleSiliconRepository modelAppleSiliconRepository;
	private final ModelAppleSiliconUnifiedMemoryRepository modelAppleSiliconUnifiedMemoryRepository;
	private final ModelStorageRepository modelStorageRepository;
	private final ModelColorRepository modelColorRepository;

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getModel(Integer modelId) {
		return NamedIdDto.from(modelRepository.getModel(modelId));
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getAppleSilicon(Integer modelAppleSiliconId) {
		return NamedIdDto.from(modelAppleSiliconRepository.getAppleSilicon(modelAppleSiliconId));

	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getUnifiedMemory(Integer modelUnifiedMemoryId) {
		return NamedIdDto.from(modelAppleSiliconUnifiedMemoryRepository.getUnifiedMemory(modelUnifiedMemoryId));
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getStorage(Integer modelStorageId) {
		return NamedIdDto.from(modelStorageRepository.getStorage(modelStorageId));
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getColor(Integer modelColorId) {
		return NamedIdDto.from(modelColorRepository.getColor(modelColorId));
	}

	@Transactional(readOnly = true)
	@Override
	public String getTechnicalSpecsUrl(Integer modelId) {
		return modelRepository.getModel(modelId).getTechnicalSpecsUrl();
	}

	@Transactional(readOnly = true)
	@Override
	public String getModelNumber(Integer modelId) {
		return modelRepository.getModel(modelId).getModelNumber();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getModelSeries(Integer modelId) {
		return NamedIdDto.from(modelRepository.getModel(modelId).getModelSeries());
	}
}