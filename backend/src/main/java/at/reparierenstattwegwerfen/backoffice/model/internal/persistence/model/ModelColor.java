package at.reparierenstattwegwerfen.backoffice.model.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "model_color")
@Getter
@Setter
@NoArgsConstructor
public class ModelColor implements NamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_color_id")
    private Integer id;

    @Column(name = "name")
    private String name;
}