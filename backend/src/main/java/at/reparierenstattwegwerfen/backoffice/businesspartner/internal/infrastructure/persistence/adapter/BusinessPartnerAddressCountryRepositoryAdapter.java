package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.adapter;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressCountry;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddressCountryRepository;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.mapper.BusinessPartnerAddressCountryPersistenceMapper;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.repository.SpringDataBusinessPartnerAddressCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.10.2026
 */
@Repository
@RequiredArgsConstructor
public class BusinessPartnerAddressCountryRepositoryAdapter implements BusinessPartnerAddressCountryRepository {
	private final SpringDataBusinessPartnerAddressCountryRepository jpaRepository;
	private final BusinessPartnerAddressCountryPersistenceMapper mapper;

	@Override
	public List<BusinessPartnerAddressCountry> findAll(Sort sort) {
		return jpaRepository.findAll(sort).stream()
			.map(mapper::toDomain)
			.toList();
	}

	@Override
	public BusinessPartnerAddressCountry getById(Integer id) {
		return mapper.toDomain(jpaRepository.getReferenceById(id));
	}

	@Override
	public BusinessPartnerAddressCountry getByCode(String code) {
		return mapper.toDomain(jpaRepository.getByCode(code));
	}
}