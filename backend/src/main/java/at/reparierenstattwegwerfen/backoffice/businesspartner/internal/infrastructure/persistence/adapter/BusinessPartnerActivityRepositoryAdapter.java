package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivity;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivityRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerActivityPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerActivityRepositoryAdapter implements BusinessPartnerActivityRepository {
	private final SpringDataBusinessPartnerActivityRepository repository;
	private final BusinessPartnerActivityPersistenceMapper mapper;

	@Override
	public List<BusinessPartnerActivity> getByIdWithRelations(Integer businessPartnerId) {
		return repository.getByIdWithRelations(businessPartnerId).stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public BusinessPartnerActivity save(BusinessPartnerActivity activity) {
		return mapper.toDomain(repository.save(mapper.toEntity(activity)));
	}
}