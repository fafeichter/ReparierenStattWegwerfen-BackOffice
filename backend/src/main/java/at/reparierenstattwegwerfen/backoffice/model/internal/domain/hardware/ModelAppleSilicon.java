package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSilicon implements NamedId {

	private Integer id;
	private String name;
	private String nameShort;
	private Integer numberCpuEfficiencyCores;
	private Integer numberCpuPerformanceCores;
	private Integer numberCpuSuperCores;
	private Integer numberGpuCores;
}
