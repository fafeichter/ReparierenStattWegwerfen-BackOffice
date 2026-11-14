package at.reparierenstattwegwerfen.backoffice.model.internal.infrastructure.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "model")
@Getter
@Setter
@NoArgsConstructor
public class ModelEntity implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model_series_id")
	private ModelSeriesEntity modelSeries;

	@Column(name = "model_number")
	private String modelNumber;

	@Column(name = "technical_specs_url")
	private String technicalSpecsUrl;

	@Column(name = "release_year")
	private Short releaseYear;

	@Column(name = "release_month")
	private Short releaseMonth;

	@Column(name = "display_size")
	private Short displaySize;

	@Column(name = "display_size_exact")
	private BigDecimal displaySizeExact;

	@ManyToMany
	@JoinTable(name = "model_available_color",
		joinColumns = @JoinColumn(name = "model_id"),
		inverseJoinColumns = @JoinColumn(name = "model_color_id"))
	@OrderBy("sortOrder")
	private Set<ModelColorEntity> availableColors;

	@ManyToMany
	@JoinTable(name = "model_available_feature",
		joinColumns = @JoinColumn(name = "model_id"),
		inverseJoinColumns = @JoinColumn(name = "model_feature_id"))
	@OrderBy("sortOrder")
	private Set<ModelFeatureEntity> availableFeatures;

	@OneToMany(mappedBy = "model")
	private Set<ModelAvailableAppleSiliconEntity> availableAppleSilicons;
}