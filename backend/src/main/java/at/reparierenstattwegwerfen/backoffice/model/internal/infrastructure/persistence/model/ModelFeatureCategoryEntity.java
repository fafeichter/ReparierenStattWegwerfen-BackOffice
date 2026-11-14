package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model_feature_category")
@Getter
@Setter
@NoArgsConstructor
public class ModelFeatureCategoryEntity implements Sortable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_feature_category_id")
	private Integer id;

	@Column(name = "value")
	private String value;

	@Column(name = "sort_order")
	private Integer sortOrder;
}