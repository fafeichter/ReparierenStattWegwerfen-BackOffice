package at.reparierenstattwegwerfen.backoffice.model.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_apple_silicon_unified_memory")
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSiliconUnifiedMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_apple_silicon_unified_memory_id")
    private Integer id;

    @Column(name = "size")
    private Short size;

    @Column(name = "unit")
    private String unit;
}
