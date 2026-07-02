package at.reparierenstattwegwerfen.backoffice.shared;

import java.io.Serializable;

/**
 * @author Fabian Feichter
 */
public interface NamedId extends Serializable {
	Integer getId();

	String getName();
}