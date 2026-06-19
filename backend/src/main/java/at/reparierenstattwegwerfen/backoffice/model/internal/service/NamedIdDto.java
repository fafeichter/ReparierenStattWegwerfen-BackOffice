package at.reparierenstattwegwerfen.backoffice.model.internal.service;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author Fabian Feichter
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NamedIdDto {

    Integer id;
    String name;

    public static NamedIdDto from(NamedId namedId) {
        if (namedId == null) {
            return null;
        }
        return new NamedIdDto(namedId.getId(), namedId.getName());
    }
}
