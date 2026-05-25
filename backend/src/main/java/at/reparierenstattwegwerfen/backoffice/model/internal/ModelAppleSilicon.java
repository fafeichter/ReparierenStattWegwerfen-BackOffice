package at.reparierenstattwegwerfen.backoffice.model.internal;

import at.reparierenstattwegwerfen.backoffice.shared.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_apple_silicon")
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSilicon implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_apple_silicon_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_short")
    private String nameShort;

    @Column(name = "number_efficiency_cores")
    private Integer numberEfficiencyCores;

    @Column(name = "number_performance_cores")
    private Integer numberPerformanceCores;

    @Column(name = "number_super_cores")
    private Integer numberSuperCores;
}