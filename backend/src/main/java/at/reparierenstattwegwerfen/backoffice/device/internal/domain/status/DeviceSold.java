package at.reparierenstattwegwerfen.backoffice.device.internal.domain.status;

import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 28.10.2026
 */
@Getter
public class DeviceSold extends DeviceStatusChanged {

	public DeviceSold(Object source, UserDetails actor, Integer deviceId) {
		super(source, actor, deviceId, 6);
	}
}