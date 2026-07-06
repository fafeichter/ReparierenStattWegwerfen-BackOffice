package at.reparierenstattwegwerfen.backoffice.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 */
public interface ModelDetailsService {

	NamedIdDto getModel(Integer deviceId);

	NamedIdDto getAppleSilicon(Integer deviceId);

	NamedIdDto getUnifiedMemory(Integer deviceId);

	NamedIdDto getStorage(Integer deviceId);

	NamedIdDto getColor(Integer deviceId);

	String getTechnicalSpecsUrl(Integer deviceId);

	String getModelNumber(Integer modelId);
}