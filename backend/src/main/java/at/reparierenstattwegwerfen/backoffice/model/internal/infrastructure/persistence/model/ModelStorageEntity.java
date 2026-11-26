package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model_storage")
@Getter
@Setter
public class ModelStorageEntity implements NamedId, Sortable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_storage_id")
	private Integer id;

	@Column(name = "size")
	private Short size;

	@Column(name = "unit")
	@Convert(converter = DataUnitConverter.class)
	private DataUnit unit;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@Override
	public String getName() {
		return size + " " + (unit == DataUnit.GIGABYTES ? "GB" : "TB");
	}
}