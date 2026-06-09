package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_available_apple_silicon")
@Getter
@Setter
@NoArgsConstructor
public class ModelAvailableAppleSilicon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_available_apple_silicon_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_apple_silicon_id")
    private ModelAppleSilicon modelAppleSilicon;

    @OneToMany(mappedBy = "availableAppleSilicon")
    private Set<ModelSiliconAvailableStorage> availableStorages = new HashSet<>();

    @OneToMany(mappedBy = "availableAppleSilicon")
    private Set<ModelSiliconAvailableUnifiedMemory> availableUnifiedMemories = new HashSet<>();
}