package at.reparierenstattwegwerfen.backoffice.device.internal.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class CreateNewDevice {

	@NotNull
	private final Integer modelId;
	private final Integer modelColorId;
	private final Integer modelAppleSiliconId;
	private final Integer modelAppleSiliconUnifiedMemoryId;
	private final Integer modelStorageId;
	private final String serialNumber;

	@NotNull
	private final Double purchasePrice;

	private final String defect;
	private final Integer batteryMaximumCapacity;
	private final Integer batteryCycleCount;

	private final Integer sellerBusinessPartnerId;
}