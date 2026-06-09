package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

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

    @Column(name = "number_cpu_efficiency_cores")
    private Integer numberCpuEfficiencyCores;

    @Column(name = "number_cpu_performance_cores")
    private Integer numberCpuPerformanceCores;

    @Column(name = "number_cpu_super_cores")
    private Integer numberCpuSuperCores;

    @Column(name = "number_gpu_cores")
    private Integer numberGpuCores;
}