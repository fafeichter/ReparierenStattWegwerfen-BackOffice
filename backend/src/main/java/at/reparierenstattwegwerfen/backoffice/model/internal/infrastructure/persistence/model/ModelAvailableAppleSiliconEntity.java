package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
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

	@ManyToMany
	@JoinTable(name = "model_silicon_available_storage",
		joinColumns = @JoinColumn(name = "model_available_apple_silicon_id"),
		inverseJoinColumns = @JoinColumn(name = "model_storage_id"))
	@OrderBy("sortOrder")
	private Set<ModelStorageEntity> availableStorages;

	@ManyToMany
	@JoinTable(name = "model_silicon_available_unified_memory",
		joinColumns = @JoinColumn(name = "model_available_apple_silicon_id"),
		inverseJoinColumns = @JoinColumn(name = "model_apple_silicon_unified_memory_id"))
	@OrderBy("sortOrder")
	private Set<ModelAppleSiliconUnifiedMemoryEntity> availableUnifiedMemories;
}