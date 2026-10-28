package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerRepository {
	List<BusinessPartner> findAllBusinessPartners();

	BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId);

	BusinessPartner getBusinessPartner(Integer businessPartnerId);

	BusinessPartner getById(Integer id);

	BusinessPartner save(BusinessPartner businessPartner);
}