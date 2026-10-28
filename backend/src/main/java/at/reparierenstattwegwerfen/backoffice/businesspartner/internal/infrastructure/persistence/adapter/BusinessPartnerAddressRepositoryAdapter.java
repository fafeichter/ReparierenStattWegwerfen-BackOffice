package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerAddressPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerAddressRepositoryAdapter implements BusinessPartnerAddressRepository {
	private final SpringDataBusinessPartnerAddressRepository repository;
	private final BusinessPartnerAddressPersistenceMapper mapper;

	@Override
	public BusinessPartnerAddress getById(Integer id) {
		return mapper.toDomain(repository.getReferenceById(id));
	}

	@Override
	public BusinessPartnerAddress save(BusinessPartnerAddress address) {
		return mapper.toDomain(repository.save(mapper.toEntity(address)));
	}
}