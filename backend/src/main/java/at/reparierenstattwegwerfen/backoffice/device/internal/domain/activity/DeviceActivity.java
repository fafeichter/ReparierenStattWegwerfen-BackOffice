package at.reparierenstattwegwerfen.backoffice.device.internal.domain.activity;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class DeviceActivity implements NamedId {

	private Integer id;
	private String name;
	private String actor;
	private Integer deviceId;
	private DeviceActivityType activityType;
	private LocalDateTime date;

	public DeviceActivity(LocalDateTime date, UserDetails actor) {
		this.date = date;
		this.actor = actor.getUsername();
	}
}