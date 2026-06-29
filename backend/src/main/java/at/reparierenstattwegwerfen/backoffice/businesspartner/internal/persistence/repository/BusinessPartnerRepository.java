package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.shared.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Repository
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Integer> {

	@Query("""
		from BusinessPartner bp
		left join fetch bp.address a
		left join fetch a.country
		order by bp.lastName asc, bp.firstName asc
		""")
	List<BusinessPartner> findAllBusinessPartners();

	@Query("""
		select bp
		from BusinessPartner bp
		left join fetch bp.address a
		left join fetch a.country
		where bp.id = :businessPartnerId
		""")
	BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId);

	@Query("""
		select bp
		from BusinessPartner bp
		where bp.id = :businessPartnerId
		""")
	NamedId getBusinessPartner(Integer businessPartnerId);
}