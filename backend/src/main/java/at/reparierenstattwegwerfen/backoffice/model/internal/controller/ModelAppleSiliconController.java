package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import at.reparierenstattwegwerfen.backoffice.model.internal.service.ModelAppleSiliconService;
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
@RequestMapping("/api/models/{modelId}/apple-silicons")
@RequiredArgsConstructor
public class ModelAppleSiliconController {

	private final ModelAppleSiliconService modelAppleSiliconService;

	@GetMapping("/")
	public List<NamedIdDto> getAllAppleSiliconsForModel(@PathVariable Integer modelId) {
		return modelAppleSiliconService.getAllAppleSiliconsForModel(modelId);
	}
}