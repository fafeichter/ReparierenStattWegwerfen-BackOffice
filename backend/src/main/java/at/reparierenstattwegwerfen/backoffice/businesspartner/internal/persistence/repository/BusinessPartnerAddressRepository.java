package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
@Repository
public interface BusinessPartnerAddressRepository extends JpaRepository<BusinessPartnerAddress, Integer> {

}