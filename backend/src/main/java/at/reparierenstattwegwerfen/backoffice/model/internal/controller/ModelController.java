package at.reparierenstattwegwerfen.backoffice.model.internal.controller;

import at.reparierenstattwegwerfen.backoffice.model.internal.service.ModelService;
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
}
