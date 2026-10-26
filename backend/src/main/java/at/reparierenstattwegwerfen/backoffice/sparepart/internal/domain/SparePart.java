package at.reparierenstattwegwerfen.backoffice.sparepart.internal.domain;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 25.05.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class SparePart implements NamedId {

	private Integer id;
	private String name;
	private Integer sortOrder;
}
