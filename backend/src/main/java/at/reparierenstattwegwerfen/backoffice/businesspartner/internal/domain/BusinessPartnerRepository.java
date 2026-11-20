package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerRepository {
	Page<BusinessPartner> search(Pageable page);

	BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId);

	BusinessPartner getById(Integer businessPartnerId);

	BusinessPartner save(BusinessPartner businessPartner);

	void markBusinessPartnerAsScammer(Integer businessPartnerId);

	void unmarkBusinessPartnerAsScammer(Integer businessPartnerId);
}