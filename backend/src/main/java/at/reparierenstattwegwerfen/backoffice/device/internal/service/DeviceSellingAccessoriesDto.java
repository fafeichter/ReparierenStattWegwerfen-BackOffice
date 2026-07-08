package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class DeviceSellingAccessoriesDto {

	@NotNull
	private final Boolean charger;

	@NotNull
	private final Boolean chargingCable;

	@NotNull
	private final Boolean originalPackaging;
}
