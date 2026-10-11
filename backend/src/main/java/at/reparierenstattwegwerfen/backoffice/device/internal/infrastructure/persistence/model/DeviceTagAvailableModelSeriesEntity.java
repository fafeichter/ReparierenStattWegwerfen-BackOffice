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
@Table(name = "device_tag_available_model_series")
@Getter
@Setter
@NoArgsConstructor
public class DeviceTagAvailableModelSeriesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_tag_available_model_series")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_tag_id")
	private DeviceTagEntity tag;

	@Column(name = "model_series_id")
	private Integer modelSeriesId;
}