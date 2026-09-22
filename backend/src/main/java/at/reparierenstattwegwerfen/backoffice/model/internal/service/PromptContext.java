package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author Fabian Feichter
 * @since 17.06.2026
 */
@Data
@Builder
public class PromptContext {
	private String adHtmlContent;
}