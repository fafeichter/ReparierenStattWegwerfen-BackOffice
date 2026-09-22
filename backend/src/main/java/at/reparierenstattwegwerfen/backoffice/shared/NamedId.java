package at.reparierenstattwegwerfen.backoffice.shared;

import java.io.Serializable;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
public interface NamedId extends Serializable {
	Integer getId();

	String getName();
}