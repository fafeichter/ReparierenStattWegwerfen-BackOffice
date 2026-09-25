package at.reparierenstattwegwerfen.backoffice.device.internal.service.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 25.09.2026
 */
@Getter
public class DeviceSparePartDeleted extends AbstractDeviceActivityEvent {

	final Integer oldDeviceSparePartId;
	final String oldDeviceSparePartName;
	final Double oldDeviceSparePartPriceNetto;

	@Builder
	public DeviceSparePartDeleted(Object source, UserDetails actor, Integer deviceId, Integer oldDeviceSparePartId,
								  String oldDeviceSparePartName, Double oldDeviceSparePartPriceNetto) {
		super(source, deviceId, actor);
		this.oldDeviceSparePartId = oldDeviceSparePartId;
		this.oldDeviceSparePartName = oldDeviceSparePartName;
		this.oldDeviceSparePartPriceNetto = oldDeviceSparePartPriceNetto;
	}
}