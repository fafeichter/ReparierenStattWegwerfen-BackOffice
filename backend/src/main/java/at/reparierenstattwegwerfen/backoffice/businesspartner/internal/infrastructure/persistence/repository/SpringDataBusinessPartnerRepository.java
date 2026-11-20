package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 16.06.2026
 */
public interface SpringDataBusinessPartnerRepository extends JpaRepository<BusinessPartnerEntity, Integer> {

	@Query("""
		from BusinessPartnerEntity bp
		left join fetch bp.address a
		left join fetch a.country
		order by bp.lastName asc, bp.firstName asc
		""")
	List<BusinessPartnerEntity> findAllBusinessPartners();

	@Query("""
		from BusinessPartnerEntity bp
		left join fetch bp.address a
		left join fetch a.country
		where bp.id = :businessPartnerId
		""")
	BusinessPartnerEntity getBusinessPartnerDetails(Integer businessPartnerId);

	@Modifying
	@Query("""
		update BusinessPartnerEntity bp
		set bp.scammer = true
		where bp.id = :businessPartnerId
		""")
	void markBusinessPartnerAsScammer(Integer businessPartnerId);

	@Modifying
	@Query("""
		update BusinessPartnerEntity bp
		set bp.scammer = false
		where bp.id = :businessPartnerId
		""")
	void unmarkBusinessPartnerAsScammer(Integer businessPartnerId);
}