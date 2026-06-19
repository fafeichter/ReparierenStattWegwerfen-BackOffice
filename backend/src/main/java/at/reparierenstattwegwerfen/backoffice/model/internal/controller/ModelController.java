package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import at.reparierenstattwegwerfen.backoffice.model.internal.service.AiResponse;
import at.reparierenstattwegwerfen.backoffice.model.internal.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Fabian Feichter
 */
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping("/macbooks")
    public List<ModelDto> getAllMacBooks() {
        return modelService.getAllMacBooks();
    }

    @GetMapping("/ipads")
    public List<ModelDto> getAllIPads() {
        return modelService.getAllIPads();
    }

    @GetMapping("/{modelId}")
    public ModelDetailDto getModelDetails(@PathVariable Integer modelId) {
        return modelService.getModelDetails(modelId);
    }

    @GetMapping("/search")
    public AiResponse getModelNumberFromAdUrl(@RequestParam("adUrl") String adUrl) throws IOException {
        return modelService.getModelDetailsFromAd(adUrl);
    }
}
