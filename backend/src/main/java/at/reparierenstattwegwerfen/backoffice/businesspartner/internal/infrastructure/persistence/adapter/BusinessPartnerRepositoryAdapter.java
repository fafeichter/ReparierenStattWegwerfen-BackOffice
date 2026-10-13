package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartner;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.BusinessPartnerRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerRepositoryAdapter implements BusinessPartnerRepository {
	private final SpringDataBusinessPartnerRepository repository;
	private final BusinessPartnerPersistenceMapper mapper;

	@Override
	public List<BusinessPartner> findAllBusinessPartners() {
		return repository.findAllBusinessPartners().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public BusinessPartner getBusinessPartnerDetails(Integer businessPartnerId) {
		return mapper.toDomain(repository.getBusinessPartnerDetails(businessPartnerId));
	}

	@Override
	public BusinessPartner getBusinessPartner(Integer businessPartnerId) {
		return mapper.toDomain(repository.getBusinessPartner(businessPartnerId));
	}

	@Override
	public BusinessPartner getReferenceById(Integer id) {
		return mapper.toDomain(repository.getReferenceById(id));
	}

	@Override
	public BusinessPartner save(BusinessPartner businessPartner) {
		return mapper.toDomain(repository.save(mapper.toEntity(businessPartner)));
	}

	@Override
	public Optional<BusinessPartner> findById(Integer id) {
		return repository.findById(id).map(mapper::toDomain);
	}
}