package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_apple_silicon_unified_memory")
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSiliconUnifiedMemory implements NamedId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_apple_silicon_unified_memory_id")
    private Integer id;

    @Column(name = "size")
    private Short size;

    @Column(name = "unit")
    @Convert(converter = DataUnitConverter.class)
    private DataUnit unit;

    @Override
    public String getName() {
        return size + " " + (unit == DataUnit.GIGABYTES ? "GB" : "TB");
    }
}
