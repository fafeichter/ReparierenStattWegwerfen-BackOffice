package at.reparierenstattwegwerfen.backoffice.shared;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * @author Fabian Feichter
 * @since 20.06.2026
 */
public record NamedIdDto(@NotNull Integer id, @NotNull String name) implements Serializable {

	public static NamedIdDto from(NamedId namedId) {
		if (namedId == null) {
			return null;
		}
		return new NamedIdDto(namedId.getId(), namedId.getName());
	}
}