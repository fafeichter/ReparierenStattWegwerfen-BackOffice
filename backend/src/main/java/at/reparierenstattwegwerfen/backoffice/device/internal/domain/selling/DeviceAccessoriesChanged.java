package at.reparierenstattwegwerfen.backoffice.device.internal.domain.selling;

import at.reparierenstattwegwerfen.backoffice.device.internal.domain.AbstractDeviceActivityEvent;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 08.11.2026
 */
@Getter
public class DeviceAccessoriesChanged extends AbstractDeviceActivityEvent {

	private final Boolean charger;
	private final Boolean chargingCable;
	private final Boolean originalPackaging;

	@Builder
	public DeviceAccessoriesChanged(Object source, UserDetails actor, Integer deviceId, Boolean charger,
									Boolean chargingCable, Boolean originalPackaging) {
		super(source, deviceId, actor);
		this.charger = charger;
		this.chargingCable = chargingCable;
		this.originalPackaging = originalPackaging;
	}

	public String getNameFormatted() {
		String chargerVal = getValue(charger);
		String chargingCableVal = getValue(chargingCable);
		String originalPackagingVal = getValue(originalPackaging);

		return String.format("Ladegerät: %s, Ladekabel: %s, OVP: %s", chargerVal, chargingCableVal, originalPackagingVal);
	}

	private String getValue(Boolean value) {
		return (value != null && value) ? "Ja" : "Nein";
	}
}