package at.reparierenstattwegwerfen.backoffice.model.internal.domain.resolution;

/**
 * Common contract shared by the AI's primary device match and each of its alternative candidates. Extracted so that
 * {@link ModelResolutionService} can resolve both shapes with the same code instead of duplicating the resolution
 * logic per type.
 *
 * @author Fabian Feichter
 * @since 24.06.2026
 */
public interface ModelMatchCandidate {

	Integer modelId();

	Integer modelAppleSiliconId();

	Integer modelColorId();

	Integer modelStorageId();

	Integer modelAppleSiliconUnifiedMemoryId();

	Integer batteryMaximumCapacity();

	Integer batteryCycleCount();

	String serialNumber();

	Integer confidence();
}