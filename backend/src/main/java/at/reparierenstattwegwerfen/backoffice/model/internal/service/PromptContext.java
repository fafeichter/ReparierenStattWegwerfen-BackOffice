package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 */
@Data
@Builder
public class PromptContext {
    private String adHtmlContent;
}