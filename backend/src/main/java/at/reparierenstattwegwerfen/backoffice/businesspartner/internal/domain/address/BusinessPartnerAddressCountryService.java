package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.infrastructure.persistence.model.BusinessPartnerAddressCountryEntity_;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerAddressCountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 13.07.2026
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerAddressCountryService {

	private final BusinessPartnerAddressCountryRepository addressCountryRepository;

	@Transactional(readOnly = true)
	public List<BusinessPartnerAddressCountryDto> getAllCountries() {
		return addressCountryRepository.findAll(Sort.by(Sort.Direction.ASC, BusinessPartnerAddressCountryEntity_.SORT_ORDER))
			.stream()
			.map(address -> BusinessPartnerAddressCountryDto.builder()
				.id(address.getId())
				.name(address.getName())
				.code(address.getCode())
				.build())
			.toList();
	}
}