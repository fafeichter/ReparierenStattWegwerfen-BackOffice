package at.reparierenstattwegwerfen.backoffice.model.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.ModelService;
import at.reparierenstattwegwerfen.backoffice.model.internal.interfaces.dtos.ModelDetailDto;
import at.reparierenstattwegwerfen.backoffice.model.internal.interfaces.dtos.ModelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 09.06.2026
 */
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

	private final ModelService modelService;

	@GetMapping("/{modelId}")
	public ModelDetailDto getModelDetails(@PathVariable Integer modelId) {
		return modelService.getModelDetails(modelId);
	}

	@GetMapping("/macbooks")
	public List<ModelDto> getAllMacBooks() {
		return modelService.getAllMacBooks();
	}

	@GetMapping("/ipads")
	public List<ModelDto> getAllIPads() {
		return modelService.getAllIPads();
	}
}