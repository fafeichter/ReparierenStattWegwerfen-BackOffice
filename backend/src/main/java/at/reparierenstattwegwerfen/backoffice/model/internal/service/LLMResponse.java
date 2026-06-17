package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @author Fabian Feichter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LLMResponse {

    private List<String> modelNumbers;

    /**
     * Helper method to easily grab the top candidate.
     * Since the LLM sorts by probability, index 0 is always the best guess.
     */
    public Optional<String> getMostLikelyModel() {
        if (modelNumbers != null && !modelNumbers.isEmpty()) {
            return Optional.of(modelNumbers.getFirst());
        }
        return Optional.empty();
    }
}