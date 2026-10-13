package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerAddressCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Fabian Feichter
 * @since 10.07.2026
 */
public interface SpringDataBusinessPartnerAddressCountryRepository extends JpaRepository<BusinessPartnerAddressCountryEntity, Integer> {
	BusinessPartnerAddressCountryEntity getByCode(String code);
}