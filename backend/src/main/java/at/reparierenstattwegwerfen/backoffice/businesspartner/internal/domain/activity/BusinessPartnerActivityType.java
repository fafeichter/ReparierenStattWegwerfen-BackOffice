package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity;

import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
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
public class BusinessPartnerActivityType implements NamedId {
	private Integer id;
	private String name;
}