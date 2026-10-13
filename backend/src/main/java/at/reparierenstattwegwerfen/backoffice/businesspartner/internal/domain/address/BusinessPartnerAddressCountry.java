package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import at.reparierenstattwegwerfen.backoffice.shared.Sortable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Getter
@Setter
@NoArgsConstructor
public class BusinessPartnerAddressCountry implements NamedId, Sortable {
	private Integer id;
	private String name;
	private String code;
	private Integer sortOrder;
}