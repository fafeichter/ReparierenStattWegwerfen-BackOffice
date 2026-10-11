package at.reparierenstattwegwerfen.backoffice.device.internal.interfaces.dtos;

import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 09.07.2026
 */
@Data
public class UpdateHardwareConfigDto {

	private final Integer modelAppleSiliconId;
	private final Integer modelAppleSiliconUnifiedMemoryId;
	private final Integer modelStorageId;
	private final Integer modelColorId;
}