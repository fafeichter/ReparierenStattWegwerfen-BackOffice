package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddressCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface BusinessPartnerAddressCountryRepository extends JpaRepository<BusinessPartnerAddressCountry, Integer> {

	BusinessPartnerAddressCountry getByCode(String code);
}