package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.model.internal.persistence.repository.*;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class ModelResolutionService {

	private final ModelRepository modelRepository;
	private final ModelColorRepository modelColorRepository;
	private final ModelAppleSiliconRepository modelAppleSiliconRepository;
	private final ModelStorageRepository modelStorageRepository;
	private final ModelAppleSiliconUnifiedMemoryRepository memoryRepository;

	@Transactional(readOnly = true)
	public ResolvedModelMatch resolveToDomainResponse(ModelMatchResponse aiResponse) {
		if (aiResponse == null) return null;

		List<ResolvedModelMatch.Alternative> alternatives = aiResponse.alternativeCandidates() == null
			? Collections.emptyList()
			: aiResponse.alternativeCandidates().stream()
			.map(this::resolveAlternative)
			.toList();

		return ResolvedModelMatch.builder()
			.model(resolveModel(aiResponse))
			.modelColor(resolveColor(aiResponse))
			.modelStorage(resolveStorage(aiResponse))
			.modelAppleSilicon(resolveSilicon(aiResponse))
			.modelAppleSiliconUnifiedMemory(resolveMemory(aiResponse))
			.batteryMaximumCapacity(aiResponse.batteryMaximumCapacity().orElse(null))
			.batteryCycleCount(aiResponse.batteryCycleCount().orElse(null))
			.reportedDefect(aiResponse.reportedDefect().orElse(null))
			.serialNumber(aiResponse.serialNumber().orElse(null))
			.confidence(aiResponse.confidence())
			.sellerFirstName(aiResponse.sellerFirstName().orElse(null))
			.sellerLastName(aiResponse.sellerLastName().orElse(null))
			.alternativeCandidates(alternatives)
			.build();
	}

	private ResolvedModelMatch.Alternative resolveAlternative(ModelMatchCandidate candidate) {
		return ResolvedModelMatch.Alternative.builder()
			.model(resolveModel(candidate))
			.modelColor(resolveColor(candidate))
			.modelStorage(resolveStorage(candidate))
			.modelAppleSilicon(resolveSilicon(candidate))
			.modelAppleSiliconUnifiedMemory(resolveMemory(candidate))
			.batteryMaximumCapacity(candidate.batteryMaximumCapacity().orElse(null))
			.batteryCycleCount(candidate.batteryCycleCount().orElse(null))
			.serialNumber(candidate.serialNumber().orElse(null))
			.confidence(candidate.confidence())
			.build();
	}

	// --- Shared lookups, used for both the primary match and every alternative candidate ---

	private NamedIdDto resolveModel(ModelMatchCandidate candidate) {
		NamedId model = modelRepository.findById(candidate.modelId()).orElse(null);
		return NamedIdDto.from(model);
	}

	private NamedIdDto resolveColor(ModelMatchCandidate candidate) {
		NamedId color = candidate.modelColorId().flatMap(modelColorRepository::findById).orElse(null);
		return NamedIdDto.from(color);
	}

	private NamedIdDto resolveStorage(ModelMatchCandidate candidate) {
		NamedId storage = candidate.modelStorageId().flatMap(modelStorageRepository::findById).orElse(null);
		return NamedIdDto.from(storage);
	}

	private NamedIdDto resolveSilicon(ModelMatchCandidate candidate) {
		NamedId silicon = candidate.modelAppleSiliconId().flatMap(modelAppleSiliconRepository::findById).orElse(null);
		return NamedIdDto.from(silicon);
	}

	private NamedIdDto resolveMemory(ModelMatchCandidate candidate) {
		NamedId memory = candidate.modelAppleSiliconUnifiedMemoryId().flatMap(memoryRepository::findById).orElse(null);
		return NamedIdDto.from(memory);
	}
}
