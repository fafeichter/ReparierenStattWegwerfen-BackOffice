package at.reparierenstattwegwerfen.backoffice.model.internal.domain.hardware;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.unit.DataUnit;

/**
 * @author Fabian Feichter
 * @since 25.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class ModelAppleSiliconUnifiedMemory implements NamedId {

	private Integer id;
	private Short size;
	private DataUnit unit;

	@Override
	public String getName() {
		return size + " " + (unit == DataUnit.GIGABYTES ? "GB" : "TB");
	}
}
