package at.reparierenstattwegwerfen.backoffice.model.internal.domain;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Getter
@Setter
public class ModelFeature implements NamedId {

	private Integer id;
	private String name;
	private ModelFeatureCategory modelFeatureCategory;
}