package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Fabian Feichter
 */
@Entity
@Table(name = "device_activity")
@Getter
@Setter
@NoArgsConstructor
public class DeviceActivity implements NamedId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_activity_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private Device device;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_activity_type_id")
	private DeviceActivityType activityType;

	@Column(name = "date")
	private LocalDateTime date;
	
	public DeviceActivity(long date) {
		this();
		this.date = Instant.ofEpochMilli(date)
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}
}