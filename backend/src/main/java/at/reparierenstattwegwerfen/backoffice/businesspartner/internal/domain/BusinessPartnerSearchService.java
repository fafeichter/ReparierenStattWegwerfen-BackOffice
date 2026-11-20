package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerAddressDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fabian Feichter
 * @since 20.11.2026
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerSearchService {

	private final BusinessPartnerRepository businessPartnerRepository;

	@Transactional(readOnly = true)
	public Page<BusinessPartnerDto> search(Pageable page) {
		return businessPartnerRepository.search(page)
			.map(businessPartner -> BusinessPartnerDto.builder()
				.id(businessPartner.getId())
				.name(businessPartner.getName())
				.firstName(businessPartner.getFirstName())
				.lastName(businessPartner.getLastName())
				.scammer(businessPartner.getScammer())
				.address(toAddress(businessPartner.getAddress()))
				.build());
	}

	private BusinessPartnerAddressDto toAddress(BusinessPartnerAddress address) {
		return address != null ? BusinessPartnerAddressDto
			.builder()
			.id(address.getId())
			.street(address.getStreet())
			.houseNumber(address.getHouseNumber())
			.zipCode(address.getZipCode())
			.city(address.getCity())
			.country(address.getCountry().getName())
			.build() : null;
	}
}