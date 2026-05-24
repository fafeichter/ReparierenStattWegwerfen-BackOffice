package at.reparierenstattwegwerfen.backoffice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "apple_silicon_unified_memory")
@Getter
@Setter
@NoArgsConstructor
public class AppleSiliconUnifiedMemory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apple_silicon_unified_memory_id")
    private Integer id;

    @Column(name = "size")
    private Short size;

    @Column(name = "unit")
    private String unit;
}
