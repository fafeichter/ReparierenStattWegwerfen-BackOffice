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
@Table(name = "device_status_classification")
@Getter
@Setter
@NoArgsConstructor
public class DeviceStatusClassification implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_status_classification_id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
