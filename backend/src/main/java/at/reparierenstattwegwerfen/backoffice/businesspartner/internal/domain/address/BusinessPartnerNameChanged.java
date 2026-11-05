package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 * @since 05.11.2026
 */
@Getter
public class BusinessPartnerNameChanged extends AbstractBusinessPartnerActivityEvent {

	private final String firstName;
	private final String lastName;

	@Builder
	public BusinessPartnerNameChanged(Object source, UserDetails actor, Integer businessPartnerId, String firstName,
									  String lastName) {
		super(source, businessPartnerId, actor);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getNameFormatted() {
		String firstNameVal = getValueOrDefault(firstName);
		String lastNameVal = getValueOrDefault(lastName);

		return String.format("%s %s", firstNameVal, lastNameVal
		);
	}

	private String getValueOrDefault(String value) {
		return (value != null && !value.isBlank()) ? value.trim() : "-";
	}
}