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
		from BusinessPartnerActivityEntity a
		left join fetch a.activityType
		where a.businessPartnerId = :businessPartnerId
		order by a.date desc
		""")
	List<BusinessPartnerActivityEntity> getByIdWithRelations(Integer businessPartnerId);
}