package at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain;

import at.reparierenstattwegwerfen.backoffice.businesspartner.BusinessPartnerDetailsService;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.domain.address.BusinessPartnerAddress;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerAddressDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerDetailDto;
import at.reparierenstattwegwerfen.backoffice.businesspartner.internal.interfaces.dtos.BusinessPartnerDto;
import at.reparierenstattwegwerfen.backoffice.shared.NamedIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fabian Feichter
 * @since 16.06.2026
 */
@Service
@RequiredArgsConstructor
public class BusinessPartnerService implements BusinessPartnerDetailsService {

	private final BusinessPartnerRepository businessPartnerRepository;

	@Transactional(readOnly = true)
	public List<BusinessPartnerDto> getAllBusinessPartners() {
		return businessPartnerRepository.findAllBusinessPartners()
			.stream()
			.map(businessPartner -> BusinessPartnerDto.builder()
				.id(businessPartner.getId())
				.name(businessPartner.getName())
				.firstName(businessPartner.getFirstName())
				.lastName(businessPartner.getLastName())
				.scammer(businessPartner.getScammer())
				.address(toAddress(businessPartner.getAddress()))
				.build())
			.toList();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedIdDto getBusinessPartner(Integer businessPartnerId) {
		return NamedIdDto.from(businessPartnerRepository.getBusinessPartner(businessPartnerId));
	}

	@Transactional(readOnly = true)
	public BusinessPartnerDetailDto getBusinessPartnerDetails(Integer businessPartnerId) {
		BusinessPartner businessPartner = businessPartnerRepository.getBusinessPartnerDetails(businessPartnerId);

		return BusinessPartnerDetailDto.builder()
			.id(businessPartner.getId())
			.name(businessPartner.getName())
			.firstName(businessPartner.getFirstName())
			.lastName(businessPartner.getLastName())
			.telephone(businessPartner.getTelephone())
			.email(businessPartner.getEmail())
			.companyName(businessPartner.getCompanyName())
			.companyUid(businessPartner.getCompanyUid())
			.scammer(businessPartner.getScammer())
			.address(toAddress(businessPartner.getAddress()))
			.build();
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