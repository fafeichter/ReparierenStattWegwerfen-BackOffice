package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model_apple_silicon")
@Getter
@Setter
public class ModelAppleSiliconEntity implements NamedId, Sortable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_apple_silicon_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_apple_silicon_variant_id")
	private ModelAppleSiliconVariantEntity variant;

	@Column(name = "number_cpu_efficiency_cores")
	private Integer numberCpuEfficiencyCores;

	@Column(name = "number_cpu_performance_cores")
	private Integer numberCpuPerformanceCores;

	@Column(name = "number_cpu_super_cores")
	private Integer numberCpuSuperCores;

	@Column(name = "number_gpu_cores")
	private Integer numberGpuCores;

	@Column(name = "sort_order")
	private Integer sortOrder;
}