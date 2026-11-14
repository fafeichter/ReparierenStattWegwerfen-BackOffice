package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 14.11.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSiliconGeneration implements NamedId {

	private Integer id;
	private String name;
}