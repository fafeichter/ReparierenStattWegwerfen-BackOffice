package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
@Data
public class DeviceSellingAccessoriesFormDto {

	@NotNull
	private final Boolean charger;

	@NotNull
	private final Boolean chargingCable;

	@NotNull
	private final Boolean originalPackaging;
}
