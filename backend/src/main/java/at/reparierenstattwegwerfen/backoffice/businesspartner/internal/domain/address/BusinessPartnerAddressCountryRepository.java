package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
public interface BusinessPartnerAddressCountryRepository {
	List<BusinessPartnerAddressCountry> findAll(Sort sort);

	BusinessPartnerAddressCountry getReferenceById(Integer id);

	BusinessPartnerAddressCountry getByCode(String code);
}
