package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "device_status")
@Getter
@Setter
@NoArgsConstructor
public class DeviceStatus implements NamedId, Sortable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_status_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "sort_order")
	private Integer sortOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_status_classification_id")
	private DeviceStatusClassification deviceStatusClassification;
}