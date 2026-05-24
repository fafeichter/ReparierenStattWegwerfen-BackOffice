package at.reparierenstattwegwerfen.backoffice.device;

import at.reparierenstattwegwerfen.backoffice.application.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_battery_status")
@Getter
@Setter
@NoArgsConstructor
public class DeviceBatteryStatus implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_battery_status_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "sort_order")
    private Integer sortOrder;
}