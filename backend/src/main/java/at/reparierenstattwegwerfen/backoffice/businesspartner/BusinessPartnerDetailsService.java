package at.reparierenstattwegwerfen.backoffice.businesspartner;

import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;

/**
 * @author Fabian Feichter
 */
public interface BusinessPartnerDetailsService {

	NamedIdDto getBusinessPartner(Integer businessPartnerId);
}