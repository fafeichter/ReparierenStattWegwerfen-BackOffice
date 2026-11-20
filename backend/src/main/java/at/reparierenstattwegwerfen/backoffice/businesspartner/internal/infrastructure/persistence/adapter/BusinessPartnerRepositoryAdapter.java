package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerRepositoryAdapter implements BusinessPartnerRepository {
	private final SpringDataBusinessPartnerRepository jpaRepository;
	private final BusinessPartnerPersistenceMapper mapper;

	@Override
	public Page<BusinessPartner> search(Pageable page) {
		return jpaRepository.search(page)
			.map(mapper::toDomain);
	}

	@Override
	public BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId) {
		return mapper.toDomain(jpaRepository.getBusinessPartnerDetails(businessPartnerId));
	}

	@Override
	public BusinessPartner getById(Integer businessPartnerId) {
		return mapper.toDomain(jpaRepository.getReferenceById(businessPartnerId));
	}

	@Override
	public BusinessPartner save(BusinessPartner businessPartner) {
		return mapper.toDomain(jpaRepository.save(mapper.toEntity(businessPartner)));
	}

	@Override
	public void markBusinessPartnerAsScammer(Integer businessPartnerId) {
		jpaRepository.markBusinessPartnerAsScammer(businessPartnerId);
	}

	@Override
	public void unmarkBusinessPartnerAsScammer(Integer businessPartnerId) {
		jpaRepository.unmarkBusinessPartnerAsScammer(businessPartnerId);
	}
}