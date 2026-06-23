package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ResolvedModelMatch implements DeviceInspection {

	@NotNull
	NamedIdDto model;

	NamedIdDto modelColor;
	NamedIdDto modelStorage;
	NamedIdDto modelAppleSilicon;
	NamedIdDto modelAppleSiliconUnifiedMemory;

	Integer batteryMaximumCapacity;
	Integer batteryCycleCount;

	String serialNumber;

	@NotNull
	String reportedDefect;

	@NotNull
	String sellerFirstName;
	String sellerLastName;

	@NotNull
	Integer confidence;

	@NotNull
	@Builder.Default
	List<Alternative> alternativeCandidates = new ArrayList<>();

	@Value
	@Builder
	public static class Alternative implements DeviceInspection {

		@NotNull
		NamedIdDto model;
		NamedIdDto modelColor;
		NamedIdDto modelStorage;
		NamedIdDto modelAppleSilicon;
		NamedIdDto modelAppleSiliconUnifiedMemory;
		Integer batteryMaximumCapacity;
		Integer batteryCycleCount;
		String serialNumber;

		@NotNull
		Integer confidence;
	}
}
