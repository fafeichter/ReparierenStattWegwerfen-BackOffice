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
@Table(name = "device_online_marketplace")
@Getter
@Setter
@NoArgsConstructor
public class DeviceOnlineMarketplace implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_online_marketplace_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;
}
