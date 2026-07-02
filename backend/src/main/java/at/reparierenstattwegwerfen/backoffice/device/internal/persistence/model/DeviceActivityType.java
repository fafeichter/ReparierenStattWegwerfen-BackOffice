package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_activity_type")
@Getter
@Setter
@NoArgsConstructor
public class DeviceActivityType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_activity_type_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description_template")
	private String descriptionTemplate;
}