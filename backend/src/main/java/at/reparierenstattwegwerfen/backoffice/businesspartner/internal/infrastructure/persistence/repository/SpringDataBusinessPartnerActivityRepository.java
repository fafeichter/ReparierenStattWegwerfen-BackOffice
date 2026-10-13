package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 19.09.2026
 */
public interface SpringDataBusinessPartnerActivityRepository extends JpaRepository<BusinessPartnerActivityEntity, Integer> {

	@Query("""
		SELECT a FROM BusinessPartnerActivityEntity a
		LEFT JOIN FETCH a.activityType
		WHERE a.businessPartner.id = :businessPartnerId
		ORDER BY a.date DESC
		""")
	List<BusinessPartnerActivityEntity> getByIdWithRelations(Integer businessPartnerId);
}