package at.reparierenstattwegwerfen.backoffice.device.internal;

import at.reparierenstattwegwerfen.backoffice.shared.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_spare_part")
@Getter
@Setter
@NoArgsConstructor
public class DeviceSparePart implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_spare_part_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price_netto")
    private BigDecimal priceNetto;
}