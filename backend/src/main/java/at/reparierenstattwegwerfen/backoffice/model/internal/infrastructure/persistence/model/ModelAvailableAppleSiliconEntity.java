package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model_available_apple_silicon")
@Getter
@Setter
@NoArgsConstructor
public class ModelAvailableAppleSiliconEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_available_apple_silicon_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	private ModelEntity model;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_apple_silicon_id")
	private ModelAppleSiliconEntity modelAppleSilicon;

	@OneToMany(mappedBy = "availableAppleSilicon")
	private Set<ModelSiliconAvailableStorageEntity> availableStorages;

	@OneToMany(mappedBy = "availableAppleSilicon")
	private Set<ModelSiliconAvailableUnifiedMemoryEntity> availableUnifiedMemories;
}