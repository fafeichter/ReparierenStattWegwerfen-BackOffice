package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 14.11.2026
 */
@Entity
@Table(name = "model_apple_silicon_variant")
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSiliconVariantEntity implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_apple_silicon_variant_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_apple_silicon_generation_id")
	private ModelAppleSiliconGenerationEntity generation;
}