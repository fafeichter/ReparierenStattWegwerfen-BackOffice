package at.reparierenstattwegwerfen.backoffice.model.internal.service;


import java.util.List;

/**
 * @author Fabian Feichter
 */
public record AiResponse(

	NamedIdDto model,
	NamedIdDto modelColor,
	NamedIdDto modelStorage,
	NamedIdDto modelAppleSiliconUnifiedMemory,
	Integer batteryMaximumCapacity,
	Integer batteryCycleCount,
	String reportedDefect,
	String serialNumber,
	List<Alternative> alternativeCandidates,
	Double time,
	String confidence
) {
	public record Alternative(

		NamedIdDto model,
		NamedIdDto modelColor,
		NamedIdDto modelStorage,
		NamedIdDto modelAppleSiliconUnifiedMemory
	) {
	}
}