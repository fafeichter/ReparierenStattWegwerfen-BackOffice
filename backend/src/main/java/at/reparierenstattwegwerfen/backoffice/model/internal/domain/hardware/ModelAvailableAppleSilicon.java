package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * An Apple Silicon chip as offered for one specific model, together with the storage and unified memory sizes it can be
 * combined with.
 *
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class ModelAvailableAppleSilicon {

	private Integer id;
	private ModelAppleSilicon modelAppleSilicon;
	private List<ModelStorage> availableStorages;
	private List<ModelAppleSiliconUnifiedMemory> availableUnifiedMemories;
}