package at.reparierenstattwegwerfen.backoffice.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
public interface ModelAppleSiliconDetailsService {

	NamedIdDto getAppleSilicon(Integer modelAppleSiliconId);
}