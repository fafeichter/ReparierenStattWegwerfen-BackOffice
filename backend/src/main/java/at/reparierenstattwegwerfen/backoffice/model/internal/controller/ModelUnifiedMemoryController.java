package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import at.reparierenstattwegwerfen.backoffice.model.internal.service.ModelUnifiedMemoryService;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/models/{modelId}/")
@RequiredArgsConstructor
public class ModelUnifiedMemoryController {

	private final ModelUnifiedMemoryService modelUnifiedMemoryService;

	@GetMapping("/{appleSiliconId}/unified-memory/")
	public List<NamedIdDto> getUnifiedMemoriesForModelAndAppleSilicon(@PathVariable Integer modelId, @PathVariable Integer appleSiliconId) {
		return modelUnifiedMemoryService.getUnifiedMemoriesForModelAndAppleSilicon(modelId, appleSiliconId);
	}
}