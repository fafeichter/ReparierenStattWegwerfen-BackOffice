package at.reparierenstattwegwerfen.backoffice.businesspartner;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Fabian Feichter
 */
public interface BusinessPartnerService {

	Integer createBusinessPartner(CreateBusinessPartnerDto businessPartnerDto, UserDetails actor);

	Integer createBusinessPartnerPlaceholder(CreateBusinessPartnerPlaceholderDto businessPartnerPlaceholder, UserDetails actor);
}