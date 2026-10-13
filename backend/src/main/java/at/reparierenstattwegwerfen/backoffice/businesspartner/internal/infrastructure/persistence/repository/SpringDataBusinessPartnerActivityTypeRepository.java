package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerActivityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
public interface SpringDataBusinessPartnerActivityTypeRepository extends JpaRepository<BusinessPartnerActivityTypeEntity, Integer> {
}