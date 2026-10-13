package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerAddressRepository {
	BusinessPartnerAddress getReferenceById(Integer id);

	BusinessPartnerAddress save(BusinessPartnerAddress address);
}