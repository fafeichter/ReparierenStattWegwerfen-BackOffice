package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
public class UpdateHardwareConfigDto {

	private final Integer modelAppleSiliconId;
	private final Integer modelAppleSiliconUnifiedMemoryId;
	private final Integer modelStorageId;
	private final Integer modelColorId;
}