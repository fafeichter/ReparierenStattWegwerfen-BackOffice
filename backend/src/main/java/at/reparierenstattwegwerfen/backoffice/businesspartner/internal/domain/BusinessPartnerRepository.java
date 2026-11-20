package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerRepository {
	List<BusinessPartner> findAllBusinessPartners();

	BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId);

	BusinessPartner getById(Integer businessPartnerId);

	BusinessPartner save(BusinessPartner businessPartner);

	void markBusinessPartnerAsScammer(Integer businessPartnerId);

	void unmarkBusinessPartnerAsScammer(Integer businessPartnerId);
}