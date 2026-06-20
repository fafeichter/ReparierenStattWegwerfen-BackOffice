package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_series")
@Getter
@Setter
@NoArgsConstructor
public class ModelSeries implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_series_id")
	private Integer id;

	@Column(name = "name")
	private String name;
}