package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import java.util.Optional;

/**
 * Common contract shared by the AI's primary device match and each of its alternative
 * candidates. Extracted so that {@link ModelResolutionService} can resolve both
 * shapes with the same code instead of duplicating the resolution logic per type.
 *
 * @author Fabian Feichter
 */
public interface ModelMatchCandidate {

	Integer modelId();

	Optional<Integer> modelAppleSiliconId();

	Optional<Integer> modelColorId();

	Optional<Integer> modelStorageId();

	Optional<Integer> modelAppleSiliconUnifiedMemoryId();

	Optional<Integer> batteryMaximumCapacity();

	Optional<Integer> batteryCycleCount();

	Optional<String> serialNumber();

	Integer confidence();
}