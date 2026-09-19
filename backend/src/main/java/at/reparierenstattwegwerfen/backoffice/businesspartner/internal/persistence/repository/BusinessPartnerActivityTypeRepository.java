package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 */
@Repository
public interface BusinessPartnerActivityTypeRepository extends JpaRepository<BusinessPartnerActivityType, Integer> {
}