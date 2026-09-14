package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * @author Fabian Feichter
 */
@Data
public class CreateNewDeviceDto {

	@NotNull
	@Valid
	private final CreateNewDeviceDto.BusinessPartnerPlaceholderDto businessPartnerPlaceholder;

	@NotEmpty
	@URL
	private final String url;

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

	@Data
	static class BusinessPartnerPlaceholderDto {

		@NotEmpty
		@Size(max = 256)
		private final String firstName;

		@Size(max = 256)
		private final String lastName;
	}
}