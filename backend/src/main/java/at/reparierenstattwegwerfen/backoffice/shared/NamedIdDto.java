package at.reparierenstattwegwerfen.backoffice.shared;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * @author Fabian Feichter
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NamedIdDto implements Serializable {

	@NotNull
	Integer id;

	@NotNull
	String name;

	public static NamedIdDto from(NamedId namedId) {
		if (namedId == null) {
			return null;
		}
		return new NamedIdDto(namedId.getId(), namedId.getName());
	}
}