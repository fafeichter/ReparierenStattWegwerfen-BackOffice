package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
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
public class BusinessPartner implements NamedId {
	private Integer id;
	private String firstName;
	private String lastName;
	private String telephone;
	private String email;
	private String companyName;
	private String companyUid;
	private Boolean scammer;
	private BusinessPartnerAddress address;

	@Override
	public String getName() {
		return companyName != null ? companyName : firstName + " " + lastName;
	}
}