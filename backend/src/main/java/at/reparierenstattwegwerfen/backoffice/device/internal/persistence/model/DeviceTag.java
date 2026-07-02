package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_tag")
@Getter
@Setter
@NoArgsConstructor
public class DeviceTag implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_tag_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "sort_order")
	private Integer sortOrder;
}
