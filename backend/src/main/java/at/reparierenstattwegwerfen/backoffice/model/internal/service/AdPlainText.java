package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * The ad page's raw HTML with all markup, scripts, and boilerplate stripped away,
 * leaving just the plain text that's relevant to feed to the AI.
 *
 * @author Fabian Feichter
 */
@Getter
public class AdPlainText {

	// Pre-compile patterns for performance
	private static final Pattern CODE_BLOCKS = Pattern.compile("<(script|style|noscript|meta|link|iframe|header|footer|nav|aside|img)[^>]*?>[\\s\\S]*?</\\1>", Pattern.CASE_INSENSITIVE);
	private static final Pattern ALL_TAGS = Pattern.compile("<[^>]*>");
	private static final Pattern HTML_ENTITIES = Pattern.compile("&nbsp;|&amp;|&lt;|&gt;|&quot;|&#\\d+;");
	private static final Pattern WHITESPACE = Pattern.compile("\\s+");

	private final String text;

	public AdPlainText(String html) {
		this.text = stripHtmlTrash(html);
	}

	private static String stripHtmlTrash(String html) {
		if (html == null || html.isBlank()) return "";

		// 1. Nukes scripts, styles, headers, footers, and nav bars entirely (including content inside them)
		String clean = CODE_BLOCKS.matcher(html).replaceAll(" ");

		// 2. Strips all remaining HTML tags (leaving just the raw text)
		//clean = ALL_TAGS.matcher(clean).replaceAll(" ");

		// 3. Quick cleanup for common HTML entities
		clean = HTML_ENTITIES.matcher(clean).replaceAll(" ");

		// 4. Smashes all multi-spaces, tabs, and newlines into a single space
		return WHITESPACE.matcher(clean).replaceAll(" ").trim();
	}
}
