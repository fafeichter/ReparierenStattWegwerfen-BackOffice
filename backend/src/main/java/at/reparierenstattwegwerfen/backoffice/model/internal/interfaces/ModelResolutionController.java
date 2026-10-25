package at.reparierenstattwegwerfen.backoffice.model.internal.interfaces;

import at.reparierenstattwegwerfen.backoffice.model.internal.domain.resolution.ModelResolutionService;
import at.reparierenstattwegwerfen.backoffice.model.internal.domain.resolution.ResolvedModelMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@RestController
@RequestMapping("/api/models/resolve")
@RequiredArgsConstructor
public class ModelResolutionController {

	private final ModelResolutionService modelResolutionService;

	@GetMapping("/")
	public ResolvedModelMatch resolveModelDetailsFromAd(@RequestParam("adUrl") String adUrl) {
		return modelResolutionService.resolveModelDetailsFromAd(adUrl);
	}
}