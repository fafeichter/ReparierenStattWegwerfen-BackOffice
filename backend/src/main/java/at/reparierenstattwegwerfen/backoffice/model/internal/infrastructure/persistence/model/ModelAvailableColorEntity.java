package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model_available_color")
@Getter
@Setter
@NoArgsConstructor
public class ModelAvailableColorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_available_color_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id")
	private ModelEntity model;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_color_id")
	private ModelColorEntity color;
}