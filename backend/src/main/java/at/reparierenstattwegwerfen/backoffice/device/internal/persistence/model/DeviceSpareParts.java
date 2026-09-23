package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Entity
@Table(name = "device_spare_parts")
@Getter
@Setter
@NoArgsConstructor
public class DeviceSpareParts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_spare_part_id")
	private Integer id;

	@Column(name = "device_id")
	private Integer deviceId;

	@Column(name = "spare_part_id")
	private Integer sparePartId;

	@Column(name = "price_netto")
	private Double priceNetto;

	@Column(name = "date")
	private LocalDateTime date;
}