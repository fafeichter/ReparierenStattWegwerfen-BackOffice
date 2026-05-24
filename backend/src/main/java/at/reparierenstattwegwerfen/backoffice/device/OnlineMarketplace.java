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
@Table(name = "online_marketplace")
@Getter
@Setter
@NoArgsConstructor
public class OnlineMarketplace implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "online_marketplace_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;
}
