package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 */
@Value
@RequiredArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode
public class DeviceModelMatchResponse {

	@JsonProperty(required = true, value = "model_id")
	@JsonPropertyDescription("Identifier of the matched device model (primary key in the devices reference table).")
	Integer modelId;

	@JsonProperty(required = true, value = "model_apple_silicon_id")
	@JsonPropertyDescription("Identifier for the Apple Silicon chip variant.")
	Optional<Integer> modelAppleSiliconId;

	@JsonProperty(required = true, value = "model_color_id")
	@JsonPropertyDescription("Identifier for the specific device color.")
	Optional<Integer> modelColorId;

	@JsonProperty(required = true, value = "model_storage_id")
	@JsonPropertyDescription("Identifier for the storage capacity.")
	Optional<Integer> modelStorageId;

	@JsonProperty(required = true, value = "model_apple_silicon_unified_memory_id")
	@JsonPropertyDescription("Identifier for the unified memory capacity.")
	Optional<Integer> modelAppleSiliconUnifiedMemoryId;

	@JsonProperty(required = true, value = "battery_maximum_capacity")
	@JsonPropertyDescription("The maximum battery capacity percentage between 0 and 100 inclusive.")
	Optional<Integer> batteryMaximumCapacity;

	@JsonProperty(required = true, value = "battery_cycle_count")
	@JsonPropertyDescription("Total number of battery charge cycles completed.")
	Optional<Integer> batteryCycleCount;

	@JsonProperty(required = true, value = "reported_defect")
	@JsonPropertyDescription("The defect the device has according to the seller. Must be always be in German.")
	Optional<String> reportedDefect;

	@JsonProperty(required = true, value = "serial_number")
	@JsonPropertyDescription("The serial number of the device")
	Optional<String> serialNumber;

	@JsonProperty(required = true, value = "confidence")
	@JsonPropertyDescription("Confidence level of the match.")
	Confidence confidence;

	@JsonProperty(required = true, value = "alternative_candidates")
	@JsonPropertyDescription("Alternative candidate matches, ordered by descending plausibility. Empty if the match is unambiguous.")
	List<AlternativeCandidate> alternativeCandidates;

	public enum Confidence {
		HIGH,
		MEDIUM,
		LOW
	}

	@Value
	@RequiredArgsConstructor
	@Accessors(fluent = true)
	@EqualsAndHashCode
	public static class AlternativeCandidate {

		@JsonProperty(required = true, value = "model_id")
		@JsonPropertyDescription("Identifier of the candidate device model.")
		Integer modelId;

		@JsonProperty(required = true, value = "model_apple_silicon_id")
		@JsonPropertyDescription("Identifier for the Apple Silicon chip variant.")
		Optional<Integer> modelAppleSiliconId;

		@JsonProperty(required = true, value = "model_color_id")
		@JsonPropertyDescription("Identifier for the specific device color.")
		Optional<Integer> modelColorId;

		@JsonProperty(required = true, value = "model_storage_id")
		@JsonPropertyDescription("Identifier for the storage capacity.")
		Optional<Integer> modelStorageId;

		@JsonProperty(required = true, value = "model_apple_silicon_unified_memory_id")
		@JsonPropertyDescription("Identifier for the unified memory capacity.")
		Optional<Integer> modelAppleSiliconUnifiedMemoryId;
	}
}