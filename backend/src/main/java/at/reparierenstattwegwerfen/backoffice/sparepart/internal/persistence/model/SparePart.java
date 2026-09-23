package at.reparierenstattwegwerfen.backoffice.sparepart.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 23.09.2026
 */
@Entity
@Table(name = "spare_part")
@Getter
@Setter
@NoArgsConstructor
public class SparePart implements NamedId, Sortable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spare_part_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "sort_order")
	private Integer sortOrder;
}