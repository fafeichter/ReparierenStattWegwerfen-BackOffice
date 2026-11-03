package at.reparierenstattwegwerfen.backoffice.device.internal.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "device_added_tag")
@Getter
@Setter
@NoArgsConstructor
public class DeviceAddedTagEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_added_tag_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private DeviceEntity device;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_tag_id")
	private DeviceTagEntity deviceTag;
}
