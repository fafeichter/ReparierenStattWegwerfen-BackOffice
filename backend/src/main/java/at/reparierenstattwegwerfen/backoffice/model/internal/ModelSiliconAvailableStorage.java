package at.reparierenstattwegwerfen.backoffice.model.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_silicon_available_storage")
@Getter
@Setter
@NoArgsConstructor
public class ModelSiliconAvailableStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_silicon_available_storage_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_available_apple_silicon_id")
    private ModelAvailableAppleSilicon availableAppleSilicon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_storage_id")
    private ModelStorage storage;
}
