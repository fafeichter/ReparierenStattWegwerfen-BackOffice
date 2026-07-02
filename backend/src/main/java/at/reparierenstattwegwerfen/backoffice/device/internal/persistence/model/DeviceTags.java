package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_tags")
@Getter
@Setter
@NoArgsConstructor
public class DeviceTags {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_tags_id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private Device device;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_tag_id")
	private DeviceTag deviceTag;
}
