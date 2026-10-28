package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivityType;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.activity.BusinessPartnerActivityTypeRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerActivityTypePersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerActivityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerActivityTypeRepositoryAdapter implements BusinessPartnerActivityTypeRepository {
	private final SpringDataBusinessPartnerActivityTypeRepository repository;
	private final BusinessPartnerActivityTypePersistenceMapper mapper;

	@Override
	public BusinessPartnerActivityType getById(Integer id) {
		return mapper.toDomain(repository.getReferenceById(id));
	}
}