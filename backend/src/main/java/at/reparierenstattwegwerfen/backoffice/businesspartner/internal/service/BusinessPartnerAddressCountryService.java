package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.service;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.controller.BusinessPartnerAddressCountryDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.model.BusinessPartnerAddressCountry_;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.persistence.repository.BusinessPartnerAddressCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabian Feichter
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerAddressCountryService {

	private final BusinessPartnerAddressCountryRepository addressCountryRepository;

	public List<BusinessPartnerAddressCountryDto> getAllCountries() {
		return addressCountryRepository.findAll(Sort.by(Sort.Direction.ASC, BusinessPartnerAddressCountry_.SORT_ORDER))
			.stream()
			.map(address -> BusinessPartnerAddressCountryDto.builder()
				.id(address.getId())
				.name(address.getName())
				.code(address.getCode())
				.build())
			.toList();
	}
}