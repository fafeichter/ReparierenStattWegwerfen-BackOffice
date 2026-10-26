package at.reparierenstattwegwerfen.backoffice.sparepart.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "spare_part_available_model_series")
@Getter
@Setter
@NoArgsConstructor
public class SparePartAvailableModelSeriesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spare_part_available_model_series")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spare_part_id")
	private SparePartEntity sparePart;

	@Column(name = "model_series_id")
	private Integer modelSeriesId;
}