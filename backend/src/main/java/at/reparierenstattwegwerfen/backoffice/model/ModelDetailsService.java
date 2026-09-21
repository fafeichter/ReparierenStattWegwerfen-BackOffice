package at.reparierenstattwegwerfen.backoffice.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 */
public interface ModelDetailsService {

	NamedIdDto getModel(Integer modelId);

	NamedIdDto getAppleSilicon(Integer modelAppleSiliconId);

	NamedIdDto getUnifiedMemory(Integer modelUnifiedMemoryId);

	NamedIdDto getStorage(Integer modelStorageId);

	NamedIdDto getColor(Integer modelColorId);

	String getTechnicalSpecsUrl(Integer modelId);

	String getModelNumber(Integer modelId);
}