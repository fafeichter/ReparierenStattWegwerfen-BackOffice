package at.reparierenstattwegwerfen.backoffice.businesspartner;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 * @since 29.06.2026
 */
public interface BusinessPartnerDetailsService {

	NamedIdDto getBusinessPartner(Integer businessPartnerId);
}