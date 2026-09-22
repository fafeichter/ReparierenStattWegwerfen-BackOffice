package at.reparierenstattwegwerfen.backoffice.device.internal.persistence.model;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
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

	@Column(name = "actor")
	private String actor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	private Device device;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_activity_type_id")
	private DeviceActivityType activityType;

	@Column(name = "date")
	private LocalDateTime date;

	public DeviceActivity(LocalDateTime date, UserDetails actor) {
		this.date = date;
		this.actor = actor.getUsername();
	}
}