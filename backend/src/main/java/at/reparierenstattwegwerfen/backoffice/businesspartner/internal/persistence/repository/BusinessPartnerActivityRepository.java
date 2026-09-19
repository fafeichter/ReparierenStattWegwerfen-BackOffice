package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface BusinessPartnerActivityRepository extends JpaRepository<BusinessPartnerActivity, Integer> {

	@Query("""
		SELECT a FROM BusinessPartnerActivity a
		LEFT JOIN FETCH a.activityType
		WHERE a.businessPartner.id = :businessPartnerId
		ORDER BY a.date DESC
		""")
	List<BusinessPartnerActivity> getByIdWithRelations(Integer businessPartnerId);
}