package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivity;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivityRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerActivityPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerActivityRepositoryAdapter implements BusinessPartnerActivityRepository {
	private final SpringDataBusinessPartnerActivityRepository jpaRepository;
	private final BusinessPartnerActivityPersistenceMapper mapper;

	@Override
	public List<BusinessPartnerActivity> getByIdWithRelations(Integer businessPartnerId) {
		return jpaRepository.getByIdWithRelations(businessPartnerId).stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public void save(BusinessPartnerActivity activity) {
		var entity = mapper.toEntity(activity);
		jpaRepository.save(entity);
	}
}