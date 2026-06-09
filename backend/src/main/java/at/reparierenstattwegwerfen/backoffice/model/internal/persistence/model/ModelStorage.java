package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_storage")
@Getter
@Setter
@NoArgsConstructor
public class ModelStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_storage_id")
    private Integer id;

    @Column(name = "size")
    private Short size;

    @Column(name = "unit")
    @Convert(converter = DataUnitConverter.class)
    private DataUnit unit;
}