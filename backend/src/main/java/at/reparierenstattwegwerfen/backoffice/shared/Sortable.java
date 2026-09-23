package at.reparierenstattwegwerfen.backoffice.shared;

import java.io.Serializable;

/**
 * @author Fabian Feichter
 * @since 23.09.2026
 */
public interface Sortable extends Serializable {
	Integer getSortOrder();
}