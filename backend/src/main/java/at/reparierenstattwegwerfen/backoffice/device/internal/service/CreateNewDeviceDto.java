package at.reparierenstattwegwerfen.backoffice.device.internal.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

/**
 * @author Fabian Feichter
 * @since 24.06.2026
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
	@Positive
	private final Double purchasePrice;

	@PastOrPresent
	private final LocalDate buyingDate;

	@NotEmpty
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