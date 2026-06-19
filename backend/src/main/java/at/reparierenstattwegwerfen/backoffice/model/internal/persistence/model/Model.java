package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model")
@Getter
@Setter
@NoArgsConstructor
public class Model implements NamedId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_series_id")
    private ModelSeries modelSeries;

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

    @OneToMany(mappedBy = "model")
    private Set<ModelAvailableColor> availableColors = new HashSet<>();

    @OneToMany(mappedBy = "model")
    private Set<ModelAvailableFeature> availableFeatures = new HashSet<>();

    @OneToMany(mappedBy = "model")
    private Set<ModelAvailableAppleSilicon> availableAppleSilicons = new HashSet<>();
}
