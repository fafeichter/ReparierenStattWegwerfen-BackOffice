package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	public List<BusinessPartner> findAllBusinessPartners() {
		return jpaRepository.findAllBusinessPartners().stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId) {
		return mapper.toDomain(jpaRepository.getBusinessPartnerDetails(businessPartnerId));
	}

	@Override
	public BusinessPartner getBusinessPartner(Integer businessPartnerId) {
		return mapper.toDomain(jpaRepository.getBusinessPartner(businessPartnerId));
	}

	@Override
	public BusinessPartner getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public BusinessPartner save(BusinessPartner businessPartner) {
		return mapper.toDomain(jpaRepository.save(mapper.toEntity(businessPartner)));
	}
}