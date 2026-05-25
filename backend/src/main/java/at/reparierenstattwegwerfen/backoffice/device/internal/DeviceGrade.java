package at.reparierenstattwegwerfen.backoffice.device.internal;

import at.reparierenstattwegwerfen.backoffice.shared.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_grade")
@Getter
@Setter
@NoArgsConstructor
public class DeviceGrade implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_grade_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}