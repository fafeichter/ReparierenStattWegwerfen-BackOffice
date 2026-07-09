package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import at.reparierenstattwegwerfen.backoffice.model.internal.service.ModelColorService;
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
@RequestMapping("/api/models/{modelId}/colors")
@RequiredArgsConstructor
public class ModelColorController {

	private final ModelColorService modelColorService;

	@GetMapping("/")
	public List<NamedIdDto> getColorsForModel(@PathVariable Integer modelId) {
		return modelColorService.getColorsForModel(modelId);
	}
}